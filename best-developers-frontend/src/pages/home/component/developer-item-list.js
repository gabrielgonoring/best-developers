import { Component } from "react";

export default class DeveloperItemList extends Component{

    constructor(props){
        super(props);
        
    }

    render(){

        const {developer} = this.props;

        const imgStyle= {
            height: "100px",
            width: "100px"
        }

        return (

            <div className="card m-2 col-auto p-2" style={{width: "18rem"}}>
                <img src={developer.developerIconUrl} className="card-img-top rounded" alt={developer.developer} />
                <div className="card-body">
                    <h5 className="card-title">{developer.developer}</h5>
                    <a href={developer.developerWebSiteUrl} className="btn btn-primary">See GitHub</a>
                </div>
            </div>
        );
    }
}