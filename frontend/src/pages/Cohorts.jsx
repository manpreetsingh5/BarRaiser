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
            show: false
        };
    }

    handleShow = () => {this.setState({show: true})}

    handleClose = () => {this.setState({show: false})}

    handleSubmit = event => {
        let token = localStorage.getItem("accessToken");
        let form = event.target;
        let file = form.elements.image.files[0];

        const cohort = {
            name: form.elements.name.value,
            description: form.elements.description.value,
            instructor: this.props.id,
            image_path: `/cohort/${form.elements.name.value}`
        };

        let data = new FormData();
        data.append('file', file);
        data.append('cohortDTO', JSON.stringify(cohort));

        fetch('api/cohort/addCohort', {
            method: 'POST',
            body: JSON.stringify(cohort),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+ token,
            }
        })
        .then(response => {
            console.log(response)
        })
        // .then(data => {
        //     if(data === null) {
        //         console.log("wrong")
        //     }
        //     else {
        //         console.log(data, "right")
        //     }
        // })

        event.preventDefault();
    }

    render() {
        let show = this.state.show;

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

                                        <Form.Group controlId="location">
                                            <Form.Label>Location</Form.Label>
                                            <Form.Control 
                                                required
                                                type="location" 
                                                placeholder="Enter location" 
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

                                            <Button variant="primary" type="submit" onClick={this.handleClose}>
                                                Save Changes
                                            </Button>
                                        </Modal.Footer>
                                    </Form>
                                </Modal.Body>
                            </Modal>
                        </div>

                        <div className={style.emptyDiv}>
                            <Empty/>
                            <h5>No Data</h5>
                        </div>

                    </div>
                </Row>
            </Fragment>
        );
    }
}

export default Cohorts;
