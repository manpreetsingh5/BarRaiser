import React, {Fragment, Component} from 'react';
import { BrowserRouter as Link, Redirect, withRouter } from "react-router-dom";
import styles from '../style/Error.module.css';

import Button from 'react-bootstrap/Button';

class Error extends Component {
    return = () => {
        this.props.history.push("/");
    }
    render() {
        return (
            <Fragment>
                <div className={styles.container}>
                    <div className={styles.errorDiv}>
                        <h1 className={styles.errorTitle}>404</h1>
                        <h5>Whoops. Looks like you're lost. Aren't we all?</h5>
                    </div>

                    <div className={styles.returnDiv}>
                        <Button className={styles.btn} onClick={this.return}>Return</Button>
                    </div>
                </div>
            </Fragment>
        );
    }
}

export default withRouter(Error);
