import React, { Fragment, Component } from 'react';
import { withRouter } from "react-router-dom";
import Home from './Home';
import Drinks from './Drinks';
import Bars from './Bars';
import TraineeHome from'./TraineeHome';
import Load from './Load';
import TraineeBars from './TraineeBars';
import TraineeDrinks from './TraineeDrinks';
import Play from './Play';
import style from '../style/Dashboard.module.css';

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

class Dashboard extends Component {
    constructor(props) {
        super(props);
    
        this.state = {
            pane: "home",
            id: -1,
            drink: null,
            drinkId: -1,
            cohortId: -1,
            firstName: "",
            lastName: "",
            status: "",
            isLoaded: false
        };
    }

    componentDidMount() {
        let token = localStorage.getItem("accessToken")
        fetch(`api/user/getUser?player_id=${this.props.userId}`, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer '+ token,
                'Content-Type': 'application/json',
            }
        })
        .then(response => {
            if(response.status !== 200) {
                return null;
            }
            return response.json();
        })
        .then(data => {
            if(data === null) {
                console.log("oops");
            }
            else {
                console.log(data)
                setTimeout(() => {
                    this.setState({
                        id: data.id,
                        firstName: data.first_name,
                        lastName: data.last_name,
                        status: data.status,
                        isLoaded: true
                    })
                }, 1000);
            }
        })
    }

    changePane = (comp) => {
        this.setState({
            pane: comp
        });
    }

    logout = () => {
        this.props.handleLogout();
        this.props.history.push("/");
    }

    playDrink = (id, drink, cohortId) => {
        this.setState({ pane: "play", drink: drink, drinkId: id, cohortId: cohortId })
    }

    handleComplete = () => {
        this.setState({ pane: "drinks" })
    }

    render() {
        let pane = this.state.pane;
        let isLoaded = this.state.isLoaded;
        let firstName = this.state.firstName;
        let lastName = this.state.lastName;
        let status = "";
        if(this.state.status === "BARTENDER") {
            status = "Professional Bartender"
        }
        else if(this.state.status === "TRAINEE") {
            status = "Trainee Bartender"
        }

        if(!isLoaded) {
            return (
                <Load/>
            )
        }
        else {
            return (
                <Fragment>
                    <Row className={style.specialRow}>
                        <Col sm={3} className={style.sidebarCol}>
                            <div className={style.sidebarDiv}>
                                <h1 className={style.barraiser}>BarRaiser</h1>
                                <p className={style.name}>{firstName} {lastName}</p>
                                <p className={style.title}>{status}</p>
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
                                        
                                        <Button className={style.btn} type="button" onClick={() => this.changePane("bars")}>
                                            <h3 className={style.icon}><FaUsers/></h3>
                                            <h6 className={style.linkTitle}>BARS</h6>
                                        </Button>
                                        
                                        <Button className={style.btn} type="button" onClick={this.logout}>
                                            <h3 className={style.icon}><FaSignOutAlt/></h3>
                                            <h6 className={style.linkTitle}>LOGOUT</h6>
                                        </Button>
                                    </ButtonGroup>
                            </div>
                        </Col>
                        <Col sm={9} className={style.contentCol}>
                            {pane === "home" && this.state.status === "BARTENDER" && <Home id={this.state.id}/>}
                            {pane === "home" && this.state.status === "TRAINEE" && <TraineeHome id={this.state.id}/>}
                            {pane === "bars" && this.state.status === "BARTENDER" && <Bars id={this.state.id}/>}
                            {pane === "bars" && this.state.status === "TRAINEE" && <TraineeBars id={this.state.id}/>}
                            {pane === "drinks" && this.state.status === "BARTENDER" && <Drinks id={this.state.id}/>}
                            {pane === "drinks" && this.state.status === "TRAINEE" && <TraineeDrinks id={this.state.id} play={this.playDrink}/>}
                            {pane === "play" && this.state.status === "TRAINEE" && <Play id={this.state.id} handleComplete={this.handleComplete} drink={this.state.drink} drinkId={this.state.drinkId} cohortId={this.state.cohortId}/>}
                        </Col>
                    </Row>
                </Fragment>
            );
        }
    }
}

export default withRouter(Dashboard);
