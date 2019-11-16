import React, { Fragment, Component } from 'react';
import style from '../style/Cohorts.module.css';

import Row from 'react-bootstrap/Row'

class Cohorts extends Component {
    render() {
        return (
            <Fragment>
                <Row className={style.container}>
                    <div className={style.titleDiv}>
                        <h3>Cohorts</h3>
                    </div>
                </Row>
            </Fragment>
        );
    }
}

export default Cohorts;
