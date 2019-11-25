import React, { Fragment, Component } from 'react';
import Empty from './Empty';
import Load from './Load';
import TraineeList from './TraineeList'
import style from '../style/Bars.module.css';

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col'
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image';
import Table from 'react-bootstrap/Table';

import { FaSearch } from 'react-icons/fa';
import { FaPencilAlt } from 'react-icons/fa';
import { FaTrashAlt } from 'react-icons/fa';

const monthNames = ["Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.",
                    "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."];

class Bars extends Component {
    constructor(props) {
        super(props);

        this.state = {
            show: false,
            bars: [],
            isLoaded: false
        };
    }

    componentDidMount() {
        let token = localStorage.getItem("accessToken");
        console.log(token)
        fetch(`api/cohort/getCohortForUser?user_id=${this.props.id}`, {
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
            return response.json().then(
                data => {
                    if(data !== null) {
                        this.setState({
                            bars: data
                        })
                        return data;
                    }
                    return null;
                }
            ).then(bars => {
                if(bars !== null && bars.length) {
                    let processed = 0;
                    bars.forEach((bar) => {
                        fetch(`api/user/getUserByEmail?email=${bar.cohort.createdBy}`, {
                            method: 'GET',
                            headers: {
                                'Authorization': 'Bearer '+ token,
                            }
                        })
                        .then(response => {
                            if(response.status !== 200) {
                                return null;
                            }
                            return response.json();
                        })
                        .then(data => {
                            if(data !== null) {
                                console.log(data);
                                bar.createdDate = new Date(bar.cohort.createdDate);
                                let month = monthNames[bar.createdDate.getUTCMonth()];
                                let day = bar.createdDate.getUTCDate();
                                let year = bar.createdDate.getUTCFullYear();
                                bar.displayDate = month + " " + day + ", " + year;
                                bar.showDelete = false;
                                bar.showEdit = false;
                                bar.showAddTrainee = false;
                                bar.showViewTrainees = false;
                                bar.owner = `${data.first_name} ${data.last_name}`
                                processed++;
                                if(processed === bars.length) {
                                    this.callback(bars);
                                }
                            }
                        })
                    })
                }
                else {
                    this.setState({
                        isLoaded: true
                    })
                }
            })
        })
    }

    callback = (bars) => {
        this.setState({
            bars: bars,
            isLoaded: true
        })
    }

    render() {
        let show = this.state.show;
        let bars = this.state.bars;
        let barsList = []
        let isLoaded = this.state.isLoaded;
        let empty = null;

        if(bars.length) {
            if("file" in bars[0]) {
                bars.sort((a, b) => (a.name > b.name) ? 1 : -1)
                console.log(bars)
                bars.forEach(el => 
                    barsList.push(
                        <Row key={el.cohort.id}>
                            <Col sm={10}>
                            <Card className={style.card}>
                                <Card.Body>
                                    <Row className={style.barRow}>
                                            <h5 className={style.barTitle}>{el.cohort.name}</h5>
                                    </Row>
                                    <Row>
                                        <Col sm={6}>
                                            <Image src={`data:image/png;base64,${el.file}`} fluid />
                                        </Col>
                                        <Col sm={6}>
                                        <p className={style.p}>
                                                <strong className={style.strong}>Owner: </strong> 
                                                <span className={style.span}>{el.owner}</span>
                                            </p>
                                            <p className={style.p}>
                                                <strong className={style.strong}>Created: </strong> 
                                                <span className={style.span}>{el.displayDate}</span>
                                            </p>
                                            <p className={style.p}>
                                                <strong className={style.strong}>Description: </strong>
                                                <span className={style.span}>{el.cohort.description}</span>
                                            </p>
                                        </Col>
                                    </Row>
                                </Card.Body>
                            </Card>
                            </Col>
                        </Row>
                        
                    )
                )
            }
        }

        if(!barsList.length) {
            empty = (
                <div className={style.emptyDiv}>
                    <Empty/>
                    <h5>No Data</h5>
                </div>
            )
        }

        if(!isLoaded) {
            return (
                <Fragment>
                    <Row className={style.titleContainer}>
                        <div className={style.titleDiv}>
                            <h3>Bars</h3>
                        </div>
                    </Row>
                        <Load/>
                </Fragment>
            )
        }
        else {
            return (
                <Fragment>
                    <Row className={style.titleContainer}>
                        <div className={style.titleDiv}>
                            <h3>Bars</h3>
                        </div>
                    </Row>
    
                    <Row className={style.contentContainer}>
                        <div className={style.contentDiv}>
                            <div>
                                <h4>Your Bars</h4>
                            </div>
                            {empty}
                            {barsList}
                        </div>
                    </Row>
                </Fragment>
            );
        }
    }
}

export default Bars;
