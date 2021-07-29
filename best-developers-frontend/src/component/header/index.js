import { Component } from "react";
import { logout } from "../../service/auth-session-manager";
import Modal from "../modal";
import { getUser, removeUser } from "../../service/user-information-manager";
import { createHashHistory } from 'history';
import { deleteUserById } from "../../service/user-service";

export default class Header extends Component{

    constructor(props){
        super(props);

        this.history = createHashHistory();

        this.state={
            showConfirmDeleteModal:false,
            showErrorModal:false,
            errorMessage:""
        }
    }

    logOut(){
        logout();
        removeUser();
        this.history.push("/sing-in");
    }

    

    deleteUserAccount(){
        deleteUserById(getUser().id)
            .then(data => this.logOut())
            .catch(errorMessage =>{
                this.setState({errorMessage})
                this.setShowErrorModal(true);
            })
        
    }

    setShowConfirmDeleteModal(showConfirmDeleteModal){
        this.setState({showConfirmDeleteModal})
    }

    setShowErrorModal(showErrorModal){
        this.setState({showErrorModal})
    }


    render(){

        const name = getUser().name;
        const firstLetter = name.substring(0,1).toUpperCase();

        return (
            <>
            <nav className="navbar navbar-light bg-light border border-b-1">
                <div className="container-fluid">
                    <a className="navbar-brand" href="/home">BDev</a>
                    <form className="d-flex"> 
                        <div className="container">
                            <div className="btn-group dropstart">
                            <button type="button" className="btn btn-secondary rounded-circle" data-bs-toggle="dropdown" aria-expanded="false">
                                <b>{firstLetter}</b>
                            </button>
                            <ul className="dropdown-menu">
                                <li><small className="dropdown-item"><b>{name}</b></small></li>
                                <li><hr className="dropdown-divider" /></li>
                                <li><a className="dropdown-item" href="/update-user"><small>Update my informations</small></a></li>
                                <li><a className="dropdown-item" onClick={() => this.setState({showConfirmDeleteModal:true})}><small>Delete my account</small></a></li>
                                <li><hr className="dropdown-divider" /></li>
                                <li><a className="dropdown-item" onClick={()=> this.logOut()}>Log out</a></li>
                            </ul>
                            </div>
                        </div>
                    </form>
                </div>
            </nav>
            {this.showDeleteModal()}
            {this.showErrorModal()}
            </>
        );
    }

    showDeleteModal(){
        if(this.state.showConfirmDeleteModal)
            return (
                <Modal 
                    title="Delete account" 
                    message="Your account will be deleted. Are you sure?"
                    onConfirm={() => this.deleteUserAccount()}
                    onCancel={() => this.setShowConfirmDeleteModal(false)} />
            );
    }

    showErrorModal(){
        if(this.state.showErrorModal)
            return (
                <Modal 
                    title="Error" 
                    message={this.state.errorMessage}
                    onConfirm={() => this.setShowErrorModal(false)}
                    onCancel={() => this.setShowErrorModal(false)} />
            );
    }
}