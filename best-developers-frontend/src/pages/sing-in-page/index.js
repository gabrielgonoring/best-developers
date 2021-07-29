import { Component } from "react";
import "../../main.css"
import { singUp } from "../../service/auth-service";


export default class SingInPage extends Component{

    constructor(props){
        super(props);

        this.state = {
            showLoading: false,
            singInResponseErrorMessage: "",
            user:{
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

    doSingIn(event){
        event.preventDefault();

        const {user} = this.state;

        singUp(user.email, user.password)
        .then(resp => {
            this.props.history.push("/home");
        })
        .catch(error => {
            this.setState({singInResponseErrorMessage:error})
            this.showLoadingAnimation(false)
        })
    
        this.showLoadingAnimation(true)
        
    }

    showLoadingAnimation(showLoading){
        this.setState({showLoading});
    }
    
    render(){
        const {user} = this.state; 
        const {showLoading} = this.state;
        const errorMessage = this.state.singInResponseErrorMessage;

        return (
            <div className="flex-container pt-3 pb-3">
                
                <div id="sing-up-content" className="border border-1 bg-white p-4 text-center center-element">
        
                <h1>BDev</h1>
            
                <br/>

                <form className="">
                    <div className="form-floating mb-2">
                        <input required type="email" name="email" className="form-control" id="email-floating" placeholder="Email" onChange={(e)=> this.setInputValueInState(e)} value={user.email}/>
                        <label htmlFor="email-floating">Email</label>
                    </div>
                    <div className="form-floating mb-2">
                        <input required type="password" name="password" className="form-control" id="password-floating" placeholder="Password" onChange={(e)=> this.setInputValueInState(e)} value={user.password} />
                        <label htmlFor="password-floating">Password</label>
                    </div>


                    <div className="text-start">
                    <small className="text-danger">{errorMessage}</small>
                    </div>

                    <div className="d-grid gap-2 mt-4">
                        <button className="btn btn-primary" type="submit" onClick={(e)=> this.doSingIn(e)}>
                            {   
                                showLoading ? <div className="spinner-border spinner-border-sm hidden" role="status" /> : "Sing in"
                            }   
                        </button>
                    </div>

                </form>

                </div>
                
               

                <div id="sing-in-content" className="border border-1 bg-white p-4 text-center center-element mt-3"> 
                <small>Don't have an account? <a href="/sing-up">Sing up</a></small>
                </div>
            </div>
        );
    }
}