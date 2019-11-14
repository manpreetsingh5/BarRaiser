import React, { Fragment, Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch, Redirect } from "react-router-dom";
import Home from './Home';
import Drinks from './Drinks';
import Cohorts from './Cohorts';
import Help from './Help';
import style from '../style/ProfessionalDashboard.module.css';

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form';
import ButtonGroup from 'react-bootstrap/ButtonGroup';

import {FaClipboard} from "react-icons/fa";
import {FaGlassWhiskey} from "react-icons/fa";
import {FaUsers} from "react-icons/fa";
import {FaQuestionCircle} from "react-icons/fa";
import {FaSignOutAlt} from "react-icons/fa";

class ProfessionalDashboard extends Component {
    constructor(props) {
        super(props);
    
        this.state = {
            pane: "home"
        };
    }

    changePane = (comp) => {
        this.setState({
            pane: comp
        });
    }

    render() {
        let pane = this.state.pane;
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
                        <div className={style.buttonsDiv}>
                                <ButtonGroup vertical className={style.btngrp}>
                                    <Button className={style.btn} type="button" onClick={() => this.changePane("home")}>
                                        <h3 className={style.icon}><FaClipboard/></h3>
                                        <h6 className={style.linkTitle}>HOME</h6>
                                    </Button>
                                
                                    <Button className={style.btn} type="button" onClick={() => this.changePane("drinks")}>
                                        <h3 className={style.icon}><FaGlassWhiskey/></h3>
                                        <h6 className={style.linkTitle}>DRINKS</h6>
                                    </Button>
                                    
                                    <Button className={style.btn} type="button" onClick={() => this.changePane("cohorts")}>
                                        <h3 className={style.icon}><FaUsers/></h3>
                                        <h6 className={style.linkTitle}>COHORTS</h6>
                                    </Button>
                                    
                                    <Button className={style.btn} type="button" onClick={() => this.changePane("help")}>
                                        <h3 className={style.icon}><FaQuestionCircle/></h3>
                                        <h6 className={style.linkTitle}>HELP</h6>
                                    </Button>
                                    
                                    <Button className={style.btn} type="button" onClick={() => this.changePane("5")}>
                                        <h3 className={style.icon}><FaSignOutAlt/></h3>
                                        <h6 className={style.linkTitle}>LOGOUT</h6>
                                    </Button>
                                </ButtonGroup>
                        </div>
                    </Col>
                    <Col sm={9} className={style.contentCol}>
                        {pane === "home" && <Home/>}
                        {pane === "drinks" && <Drinks/>}
                        {pane === "cohorts" && <Cohorts/>}
                        {pane === "help" && <Help/>}
                    </Col>
                </Row>
            </Fragment>
        );
    }
}

export default ProfessionalDashboard;
