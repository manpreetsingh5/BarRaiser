import React, { Fragment, Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch, Redirect } from "react-router-dom";
import style from '../style/ProfessionalDashboard.module.css';

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form';


class ProfessionalDashboard extends Component {

    render() {
        return (
            <Fragment>
                <Row>
                    <Col sm={3} className={style.sidebarCol}>
                        <div className={style.sidebarDiv}>
                            <h1 className={style.barraiser}>BarRaiser</h1>
                            <p className={style.name}>First Last</p>
                            <p className={style.title}>Professional Bartender</p>
                            <hr className={style.divider} />
                        </div>
                        <div>
                        <div class="col-sm-2 mx-auto text-center">
              <i class="fas fa-clipboard fa-2x"></i>
            </div>
            <div class="col-sm-10 my-auto">
              <h6 class="text-uppercase">Dashboard</h6>
            </div>
                        </div>
                    </Col>
                    <Col sm={9} className={style.contentCol}>
                        scoop
                    </Col>
                </Row>
            </Fragment>
        );
    }
}

export default ProfessionalDashboard;
