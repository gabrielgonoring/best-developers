import { Component } from "react";
import Header from "../../component/header";
import { getUser } from "../../service/user-information-manager";
import { updateUser } from "../../service/user-service";
import "./update-user-page.css"


export default class UpdateUserPage extends Component{

    constructor(){
        super();

        this.state = {
            showLoading: false,
            errorMessage:"",
            sucessMessage:"",
            user: {
                ...getUser(),
                password:""
            }
        }
    }
    
    setInputValueInState(event){
        const name = event.target.name;
        const value = event.target.value;

        const {user} = this.state;

        user[name] = value;

        this.setState({user});
    }

    doUpdateUser(event){
        event.preventDefault();

        const {user} = this.state;

        this.state.sucessMessage = '';
        this.state.errorMessage='';

        updateUser(user.id, user.name, user.password, user.email)
        .then(response => {
            this.setState({sucessMessage:"User updated"})
        })
        .catch(errorMessage =>{
            this.setState({errorMessage})
            this.showLoadingAnimation(false)
        });
    }

    showLoadingAnimation(showLoading){
        this.setState({showLoading})
    }

    render(){

        const {user} = this.state;
        const {errorMessage} = this.state;
        const {sucessMessage} = this.state;
        const {showLoading} = this.state;


        return (
            <>
                <Header />
                
                <div className="flex-container p-3">

                    <a href="/home" className="btn btn-secondary">&laquo; Back</a>
                    
                    <div id="form-content" className="text-center center-element">

                    <form className="">
                        <div className="form-floating mb-2">
                            <input required type="email" name="email" className="form-control" id="email-floating" placeholder="Email" onChange={(e)=> this.setInputValueInState(e)} value={user.email}/>
                            <label htmlFor="email-floating">Email</label>
                        </div>
                        <div className="form-floating mb-2">
                            <input required type="text" name="name" className="form-control" id="nome-completo-floating" placeholder="Name" onChange={(e)=> this.setInputValueInState(e)} value={user.name}/>
                            <label htmlFor="nome-completo-floating">Name</label>
                        </div>
                        <div className="form-floating mb-2">
                            <input required type="password" name="password" className="form-control" id="password-floating" placeholder="Password" onChange={(e)=> this.setInputValueInState(e)} value={user.password} />
                            <label htmlFor="password-floating">Password</label>
                        </div>

                        <div className="text-start">
                            <small className="text-danger">{errorMessage}</small>
                        </div>

                        <div className="text-start">
                            <small className="text-success">{sucessMessage}</small>
                        </div>

                        <div className="d-grid gap-2 mt-4">
                            <button className="btn btn-primary" type="submit" onClick={(e)=> this.doUpdateUser(e)}>
                                {
                                    showLoading ? <div className="spinner-border spinner-border-sm hidden" role="status" /> : "Save"
                                }
                            </button>
                        </div>

                    </form>
                    </div>
                </div>
            </>
        );
    }
}   