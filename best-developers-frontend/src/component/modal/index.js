import { Component } from "react";

export default class Modal extends Component{


    render(){

        const {title} = this.props;
        const {message} = this.props;

        const {onConfirm} = this.props;
        const {onCancel} = this.props;

        let model = {
            display:'block',
            backgroundColor:'rgba(0,0,0,0.8)'
        }

        return(
            <div className="modal show fade"  style={model}>
                <div className="modal-dialog">
                    <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">{title}</h5>
                        <button type="button" className="close" onClick={()=>onCancel()}>
                        <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        <p>{message}</p>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" onClick={()=> onCancel()}>Cancel</button>
                        <button type="button" className="btn btn-primary"  onClick={() => onConfirm()}>Confirm</button>
                    </div>
                    </div>
                </div>
            </div>
        );
    }
}