import { Component } from "react";
import "./sing-up-page.css"
import "../../main.css"
import { saveUser } from "../../service/user-service";


export default class SingUpPage extends Component{

    constructor(props){
        super(props);

        this.state = {
            showLoading:false,
            errorMessage:"",
            user:{
                name:"",
                email:"",
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

    saveUserInApi(event){
        event.preventDefault();

        const {user} =  this.state;

        saveUser(user.name, user.password, user.email)
        .then(data => {
            this.props.history.push("/home");
        }).catch(error => {
            this.setState({errorMessage:error});
            this.showLoadingAnimation(false);
        });

        this.showLoadingAnimation(true)
    }

    showLoadingAnimation(showLoading){
        this.setState({showLoading});
    }

    render(){

        const {user} = this.state; 
        const {showLoading} = this.state;
        const {errorMessage} = this.state;

        return (
            <div className="flex-container pt-3 pb-3">

                <div id="sing-up-content" className="border border-1 bg-white p-4 text-center center-element">
        
                <h1>BDev</h1>
                <p className="text-muted"><b>Sing up to see the best developers</b></p>

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

                    <div className="d-grid gap-2 mt-4">
                        <button className="btn btn-primary" type="submit" onClick={(e)=> this.saveUserInApi(e)}>
                            {
                                showLoading ? <div className="spinner-border spinner-border-sm hidden" role="status" /> : "Sing up"
                            }
                        </button>
                    </div>

                </form>
                </div>



                <div id="sing-in-content" className="border border-1 bg-white p-4 text-center center-element mt-3"> 
                <small>Have an account? <a href="/sing-in">Sing in</a></small>
                </div>
            </div>
        );
    }
}