import React, { Fragment, Component } from 'react';
import Empty from './Empty';
import Load from './Load';
import style from '../style/Cohorts.module.css';

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col'
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image'

import { FaSearch } from 'react-icons/fa';
import { FaPencilAlt } from 'react-icons/fa';
import { FaTrashAlt } from 'react-icons/fa'

const monthNames = ["Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.",
                    "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."];

class Cohorts extends Component {
    constructor(props) {
        super(props);

        this.state = {
            show: false,
            cohorts: [],
            isLoaded: false
        };
    }

    componentDidMount() {
        let token = localStorage.getItem("accessToken");
        fetch(`api/cohort/viewCohorts/${this.props.id}`, {
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
                this.setState({
                    cohorts: data
                })
                return this.state.cohorts;
            }
            return null;
        })
        .then(cohorts => {
            if(cohorts !== null && cohorts.length) {
                let processed = 0;
                cohorts.forEach((cohort, index) => 
                    fetch(`api/cohort/getCohort?cohort_id=${cohort.id}`, {
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
                        cohort.file = data.file;
                        cohort.createdDate = new Date(cohort.createdDate);
                        let month = monthNames[cohort.createdDate.getUTCMonth()];
                        let day = cohort.createdDate.getUTCDate();
                        let year = cohort.createdDate.getUTCFullYear();
                        cohort.displayDate = month + " " + day + ", " + year;
                        cohort.showDelete = false;
                        processed++;
                        if(processed === cohorts.length) {
                            this.callback(cohorts);
                        }
                    })
                )
            }
            else {
                this.setState({
                    isLoaded: true
                })
            }
        })
    }

    callback = (cohorts) => {
        this.setState({
            cohorts: cohorts,
            isLoaded: true
        })
    }

    handleShow = () => {this.setState({show: true})}

    handleClose = () => {this.setState({show: false})}

    handleDeleteShow = (id) => {
        this.setState({
            cohorts: this.state.cohorts.map(el => (el.id === id ? {...el, showDelete: true} : el))
        });
    }

    handleDeleteClose = (id) => {
        this.setState({
            cohorts: this.state.cohorts.map(el => (el.id === id ? {...el, showDelete: false} : el))
        });
    }

    handleSubmit = event => {
        // console.log("hello")
        let token = localStorage.getItem("accessToken");
        let form = event.target;
        let file = form.elements.image.files[0];

        const cohort = {
            name: form.elements.name.value,
            description: form.elements.description.value,
            instructor: this.props.id,
            image_path: `/cohort/${form.elements.name.value}`
        };

        const blob = new Blob([JSON.stringify(cohort)], {
            type: 'application/json'
        });

        let data = new FormData();
        data.append('file', file);
        data.append('cohortDTO', blob);

        fetch('api/cohort/addCohort', {
            method: 'POST',
            body: data,
            headers: {
                'Authorization': 'Bearer '+ token,
            }
        })
        .then(response => {
            console.log(response)
        })
        .then(() => {
            this.handleClose();
        })

        event.preventDefault();
    }

    test = () => {
        console.log("nice");
    }

    render() {
        let show = this.state.show;
        let cohorts = this.state.cohorts;
        let cohortsList = []
        let isLoaded = this.state.isLoaded;
        let empty = null;

        if(cohorts.length) {
            if("file" in cohorts[0]) {
                cohorts.sort((a, b) => (a.name > b.name) ? 1 : -1)
                console.log(cohorts)
                cohorts.forEach(el => 
                    cohortsList.push(
                        <Row key={el.id}>
                            <Col sm={10}>
                            <Card className={style.card}>
                                <Card.Body>
                                    <Row className={style.cohortRow}>
                                            <h5 className={style.cohortTitle}>{el.name}</h5>
                                    </Row>
                                    <Row>
                                        <Col sm={6}>
                                            <Image src={`data:image/png;base64,${el.file}`} fluid />
                                        </Col>
                                        <Col sm={6}>
                                            <p className={style.p}>
                                                <strong className={style.strong}>Created: </strong> 
                                                <span className={style.span}>{el.displayDate}</span>
                                            </p>
                                            <p className={style.p}>
                                                <strong className={style.strong}>Description: </strong>
                                                <span className={style.span}>{el.description}</span>
                                            </p>
                                        </Col>
                                    </Row>
                                    <Row className={style.cohortButtonsRow}>
                                        <Col sm={8}>
                                            <Button variant="dark" onClick={this.test}>
                                                <div className={style.cohortButtonsDiv}>
                                                    <FaSearch/>
                                                    <span className={style.buttonText}>View Trainees</span>
                                                </div>
                                            </Button>
                                            <Button variant="dark" className={style.middleButton} onClick={this.test}>
                                                <div className={style.cohortButtonsDiv}>
                                                    <FaPencilAlt/>
                                                    <span className={style.buttonText}>Edit</span>
                                                </div>
                                                
                                            </Button>
                                            <Button variant="dark" onClick={() => this.handleDeleteShow(el.id)}>
                                                <div className={style.cohortButtonsDiv}>
                                                    <FaTrashAlt/>
                                                    <span className={style.buttonText}>Delete</span>
                                                </div>
                                            </Button>

                                            <Modal show={el.showDelete} onHide={() => this.handleDeleteClose(el.id)} centered dialogClassName={style.modal}>
                                                <Modal.Header className={style.deleteHeader}>
                                                    <Modal.Title>Are you sure you want to delete {el.name}?</Modal.Title>
                                                </Modal.Header>
                
                                                <Modal.Body className={style.deleteModalButtons}>
                                                    <Button variant="danger" className={style.deleteButton}>
                                                        Delete
                                                    </Button>
                                                    <Button variant="primary" onClick={() => this.handleDeleteClose(el.id)} className={style.returnButton}>
                                                        Return
                                                    </Button>
                                                </Modal.Body>
                                            </Modal>
                                            
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

        if(!cohortsList.length) {
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
                            <h3>Cohorts</h3>
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
                            <h3>Cohorts</h3>
                        </div>
                    </Row>
    
                    <Row className={style.contentContainer}>
                        <div className={style.contentDiv}>
                            <div>
                                <h4>Your Cohorts</h4>
                            </div>
    
                            <div>
                                <Button variant="primary" onClick={this.handleShow}>
                                    Add Cohort
                                </Button>
    
                                <Modal show={show} onHide={this.handleClose} centered>
                                    <Modal.Header>
                                        <Modal.Title>Add Cohort</Modal.Title>
                                    </Modal.Header>
    
                                    <Modal.Body>
                                        <Form onSubmit={this.handleSubmit}>
                                            <Form.Group controlId="name">
                                                <Form.Label>Name</Form.Label>
    
                                                <Form.Control 
                                                    required
                                                    type="name" 
                                                    placeholder="Enter name" 
                                                />
    
                                            </Form.Group>
    
                                            <Form.Group controlId="description">
                                                <Form.Label>Description</Form.Label>
                                                <Form.Control 
                                                    required
                                                    as="textarea"
                                                    placeholder="Enter description" 
                                                />
                                            </Form.Group>
    
                                            <Form.Group controlId="image">
                                                <Form.Label>Image</Form.Label>
                                                <Form.Control 
                                                    required
                                                    type="file"
                                                />
                                            </Form.Group>
    
                                            <Modal.Footer>
                                                <Button variant="secondary" onClick={this.handleClose}>
                                                    Close
                                                </Button>
    
                                                <Button variant="primary" type="submit">
                                                    Save Changes
                                                </Button>
                                            </Modal.Footer>
                                        </Form>
                                    </Modal.Body>
                                </Modal>
                            </div>
                            {empty}
                            {cohortsList}
                        </div>
                    </Row>
                </Fragment>
            );
        }
    }
}

export default Cohorts;
