import { Component } from "react";
import Header from "../../component/header";
import { getDevelopers } from "../../service/developer-service";
import DeveloperItemList from "./component/developer-item-list";

export default class HomePage extends Component{

    constructor(){
        super();

        this.state = {
            showLoading:true,
            errorMessage:'',
            page:1,
            size:30,
            developers: []
        }
    }

    componentDidMount(){
       this.searchDevelopers();
    }

    searchDevelopers(){
        getDevelopers(this.state.size, this.state.page)
        .then(developers => {
            this.setState({developers, showLoading:false, errorMessage:''})}
        )
        .catch(errorMessage => this.setState({errorMessage, showLoading:false}))
        
        this.setState({showLoading:true})
    }

    changePageDeveloper(increment){

        if(!increment && this.state.page===1)
            return;

        this.state.page = this.state.page + ((increment)? +1 : -1);

        this.searchDevelopers();
    }

    render(){

        const {developers} = this.state;
        const {showLoading} = this.state;
        const {errorMessage} = this.state;

        const Loading = () => (
            <div className="d-flex justify-content-center m-5">
                <div className="spinner-border" role="status" />
            </div>
        )

        const Developers = () => (
            <>
                <div className="row justify-content-center mt-5">
                    {
                        developers.map( (developer, index) => (
                            <DeveloperItemList key={index} developer={developer} />
                        ))
                    }
                </div>

                <nav aria-label="Page navigation ">
                    <ul className="pagination justify-content-center">
                        {this.state.page>1 && <li className="page-item"><a class="page-link" onClick={() => this.changePageDeveloper(false)}>Previous</a></li>}
                        <li className="page-item"><a class="page-link" onClick={() => this.changePageDeveloper(true)}>Next</a></li>
                    </ul>
                </nav>
            </>
        )


        const RetrySearchDevelopers = () => (
            <div className="row">
                <span>{errorMessage}</span>
                <button className="btn btn-secondary" onClick={() => this.searchDevelopers()}>Retry</button>
            </div>
        );

        return(
            <>
            <Header />
            <div className="container">
                {showLoading && <Loading />}
                {(developers.length>0 && !showLoading && !errorMessage)&& <Developers />}
                {(errorMessage && !showLoading) && <RetrySearchDevelopers /> }
            </div>
            </>
        );
    }
}

/**
 


 */