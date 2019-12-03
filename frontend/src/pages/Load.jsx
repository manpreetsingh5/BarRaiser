import React, { Fragment, Component } from 'react';
import style from '../style/Load.module.css';

import Spinner from 'react-bootstrap/Spinner';


class Load extends Component {

    render() {
        return (
            <Fragment>
                <div className={style.loadingDiv}>
                    
                    <div className={style.loadMessageDiv}>
                    <Spinner animation="border" role="status" className={style.spinner}>
                        <span className="sr-only">Loading...</span>
                    </Spinner>
                        <h4>Loading...</h4>
                    </div>
                </div>
            </Fragment>
        );
    }
}

export default Load;
