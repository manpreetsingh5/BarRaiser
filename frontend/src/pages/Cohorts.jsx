import React, { Fragment, Component } from 'react';
import Empty from './Empty';
import style from '../style/Cohorts.module.css';

import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

class Cohorts extends Component {
    constructor(props) {
        super(props);

        this.state = {
            show: false,
            cohorts: []
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
            if(cohorts !== null) {
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
                        processed++;
                        if(processed === cohorts.length) {
                            this.callback(cohorts);
                        }
                    })
                )
            }
        })
    }

    callback = (cohorts) => {this.setState({cohorts: cohorts})}

    handleShow = () => {this.setState({show: true})}

    handleClose = () => {this.setState({show: false})}

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

    render() {
        let show = this.state.show;
        let cohorts = this.state.cohorts;
        let cohortsList = []
        // let Empty = null

        // if(cohorts.length) {
        //     Empty = (
        //         <div className={style.emptyDiv}>
        //             <Empty/>
        //             <h5>No Data</h5>
        //         </div>
        //     )
        // }

        if(cohorts.length) {
            if("file" in cohorts[0]) {
                cohorts.forEach(el => {
                    console.log(el)
                    cohortsList.push(
                        <div className={style.cohortsListDiv} key={el.id}>
                            <h4>{el.name}</h4>
                            <p>{el.description}</p>
                            <img src={`data:image/png[jpg];base64,${el.file}`} />
                        </div>
                    )
                }
                    
                )
            }
        }
        
        

        // cohorts.forEach(el => 
        //     cohortsList.push(
        //         <div className={style.cohortsListDiv} key={el.id}>
        //             <h4>{el.name}</h4>
        //             <p>{el.description}</p>
        //             <img src={`data:image/png[jpg];base64,${el.file}`} />
        //         </div>
        //     )
        // )

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
                                <Modal.Header closeButton>
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
                        {cohortsList}
                        {/* {Empty}
                        {cohortsList} */}

                    </div>
                </Row>
            </Fragment>
        );
    }
}

export default Cohorts;
