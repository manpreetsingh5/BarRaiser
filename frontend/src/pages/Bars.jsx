import React, { Fragment, Component } from 'react';
import Empty from './Empty';
import Load from './Load';
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
        fetch(`api/cohort/getCohortForUser?user_id=${this.props.id}`, {
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
            // console.log(data);
            if(data !== null) {
                this.setState({
                    bars: data
                })
                return this.state.bars;
            }
            return null;
        })
        .then(bars => {
            if(bars !== null && bars.length) {
                let processed = 0;
                bars.forEach((bar) => 
                    fetch(`api/cohort/getCohort?cohort_id=${bar.cohort.id}`, {
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
                        // console.log(data)
                        bar.file = data.file;
                        bar.createdDate = new Date(bar.cohort.createdDate);
                        let month = monthNames[bar.createdDate.getUTCMonth()];
                        let day = bar.createdDate.getUTCDate();
                        let year = bar.createdDate.getUTCFullYear();
                        bar.displayDate = month + " " + day + ", " + year;
                        bar.showDelete = false;
                        bar.showEdit = false;
                        bar.showViewTrainees = false;
                        processed++;
                        if(processed === bars.length) {
                            this.callback(bars);
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

    callback = (bars) => {
        this.setState({
            bars: bars,
            isLoaded: true
        })
    }

    updateView = () => {
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
                    bars: data
                })
                return this.state.bars;
            }
            return null;
        })
        .then(bars => {
            if(bars !== null && bars.length) {
                let processed = 0;
                bars.forEach((bar) => 
                    fetch(`api/cohort/getCohort?cohort_id=${bar.id}`, {
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
                        bar.file = data.file;
                        bar.createdDate = new Date(bar.createdDate);
                        let month = monthNames[bar.createdDate.getUTCMonth()];
                        let day = bar.createdDate.getUTCDate();
                        let year = bar.createdDate.getUTCFullYear();
                        bar.displayDate = month + " " + day + ", " + year;
                        bar.showDelete = false;
                        bar.showEdit = false;
                        bar.showViewTrainees = false;
                        processed++;
                        if(processed === bars.length) {
                            this.callback(bars);
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

    handleShow = () => {this.setState({show: true})}

    handleClose = () => {this.setState({show: false})}

    handleDeleteShow = (id) => {
        this.setState({
            bars: this.state.bars.map(el => (el.cohort.id === id ? {...el, showDelete: true} : el))
        });
    }

    handleDeleteClose = (id) => {
        this.setState({
            bars: this.state.bars.map(el => (el.cohort.id === id ? {...el, showDelete: false} : el))
        });
    }

    handleViewTraineesShow = (id, bar) => {
        let token = localStorage.getItem("accessToken");
        // console.log(bar)
        // console.log("trainees" in bar)
        if("trainees" in bar) {
            this.setState({
                bars: this.state.bars.map(el => (el.cohort.id === id ? {...el, showViewTrainees: true} : el))
            });
        }
        else {
            fetch(`api/cohort/getTraineesInCohort?cohort_id=${id}`, {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer '+ token,
                }
            })
            .then(response => {
                console.log(response)
                if(response.status !== 200) {
                    return null;
                }
                return response.json();
            })
            .then(data => {
                if(data !== null) {
                    this.setState({
                        bars: this.state.bars.map(el => (el.cohort.id === id ? {...el, showViewTrainees: true, trainees: data} : el))
                    });
                }
            })
        }
    }

    handleViewTraineesClose = (id) => {
        this.setState({
            bars: this.state.bars.map(el => (el.cohort.id === id ? {...el, showViewTrainees: false} : el))
        });
    }

    handleEditShow = (id) => {
        this.setState({
            bars: this.state.bars.map(el => (el.cohort.id === id ? {...el, showEdit: true} : el))
        });
    }

    handleEditClose = (id) => {
        this.setState({
            bars: this.state.bars.map(el => (el.cohort.id === id ? {...el, showEdit: false} : el))
        });
    }

    handleSubmit = event => {
        // console.log("hello")
        let token = localStorage.getItem("accessToken");
        let form = event.target;
        let file = form.elements.image.files[0];

        const bar = {
            name: form.elements.name.value,
            description: form.elements.description.value,
            instructor: this.props.id,
            image_path: `/cohort/${form.elements.name.value}`
        };

        const blob = new Blob([JSON.stringify(bar)], {
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
            this.updateView();
            this.handleClose();
        })

        event.preventDefault();
    }

    handleEdit = event => {
        // console.log("hello")
        let token = localStorage.getItem("accessToken");
        let form = event.target;
        let file = form.elements.image.files[0];

        const bar = {
            name: form.elements.name.value,
            description: form.elements.description.value,
            instructor: this.props.id,
            image_path: `/cohort/${form.elements.name.value}`
        };

        const blob = new Blob([JSON.stringify(bar)], {
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

    handleDelete = (id) => {
        let token = localStorage.getItem("accessToken");
        fetch(`api/cohort/deleteCohort?cohort_id=${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer '+ token,
            }
        })
        .then(response => {
            console.log(response)
        })
        .then(() => {
            this.setState({isLoaded: false});
            this.updateView();
            this.handleDeleteClose(id);
        })
    }

    test = () => {
        console.log("nice");
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
                                                <strong className={style.strong}>Created: </strong> 
                                                <span className={style.span}>{el.displayDate}</span>
                                            </p>
                                            <p className={style.p}>
                                                <strong className={style.strong}>Description: </strong>
                                                <span className={style.span}>{el.cohort.description}</span>
                                            </p>
                                        </Col>
                                    </Row>
                                    <Row className={style.barButtonsRow}>
                                        <Col sm={8}>
                                            <Button variant="dark" onClick={() => this.handleViewTraineesShow(el.cohort.id, el.cohort)}>
                                                <div className={style.barButtonsDiv}>
                                                    <FaSearch/>
                                                    <span className={style.buttonText}>View Trainees</span>
                                                </div>
                                            </Button>

                                            <Modal show={el.showViewTrainees} onHide={() => this.handleViewTraineesClose(el.cohort.id)} centered dialogClassName={style.viewTraineesModal}>
                                                <Modal.Header>
                                                    <Modal.Title>{el.cohort.name} Trainees</Modal.Title>
                                                </Modal.Header>
                
                                                <Modal.Body>
                                                <Table striped bordered hover size="sm">
                                                    <thead>
                                                        <tr>
                                                        <th>#</th>
                                                        <th>First Name</th>
                                                        <th>Last Name</th>
                                                        <th>Email</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                        <td>1</td>
                                                        <td>Mark</td>
                                                        <td>Otto</td>
                                                        <td>@mdo</td>
                                                        </tr>
                                                        <tr>
                                                        <td>2</td>
                                                        <td>Jacob</td>
                                                        <td>Thornton</td>
                                                        <td>@fat</td>
                                                        </tr>
                                                    </tbody>
                                                    </Table>
                                                </Modal.Body>
                                            </Modal>

                                            <Button variant="dark" className={style.middleButton} onClick={() => this.handleEditShow(el.cohort.id)}>
                                                <div className={style.barButtonsDiv}>
                                                    <FaPencilAlt/>
                                                    <span className={style.buttonText}>Edit</span>
                                                </div>
                                            </Button>

                                            <Modal show={el.showEdit} onHide={() => this.handleEditClose(el.cohort.id)} centered>
                                                <Modal.Header>
                                                    <Modal.Title>Edit {el.cohort.name}</Modal.Title>
                                                </Modal.Header>
                
                                                <Modal.Body>
                                                    <Form onSubmit={this.handleEdit}>
                                                        <Form.Group controlId="name">
                                                            <Form.Label>Name</Form.Label>
                
                                                            <Form.Control 
                                                                required
                                                                type="name" 
                                                                defaultValue={el.name}
                                                                placeholder="Enter name" 
                                                            />
                
                                                        </Form.Group>
                
                                                        <Form.Group controlId="description">
                                                            <Form.Label>Description</Form.Label>
                                                            <Form.Control 
                                                                required
                                                                as="textarea"
                                                                defaultValue={el.cohort.description}
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
                                                            <Button variant="secondary" onClick={() => this.handleEditClose(el.cohort.id)}>
                                                                Close
                                                            </Button>
                
                                                            <Button variant="primary" type="submit">
                                                                Save Changes
                                                            </Button>
                                                        </Modal.Footer>
                                                    </Form>
                                                </Modal.Body>
                                            </Modal>

                                            <Button variant="dark" onClick={() => this.handleDeleteShow(el.cohort.id)}>
                                                <div className={style.barButtonsDiv}>
                                                    <FaTrashAlt/>
                                                    <span className={style.buttonText}>Delete</span>
                                                </div>
                                            </Button>

                                            <Modal show={el.showDelete} onHide={() => this.handleDeleteClose(el.cohort.id)} centered dialogClassName={style.deleteModal}>
                                                <Modal.Header className={style.deleteHeader}>
                                                    <Modal.Title>Are you sure you want to delete {el.name}?</Modal.Title>
                                                </Modal.Header>
                
                                                <Modal.Body className={style.deleteModalButtons}>
                                                    <Button variant="danger" className={style.deleteButton} onClick={() => this.handleDelete(el.cohort.id)}>
                                                        Delete
                                                    </Button>
                                                    <Button variant="primary" onClick={() => this.handleDeleteClose(el.cohort.id)} className={style.returnButton}>
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
    
                            <div>
                                <Button variant="primary" onClick={this.handleShow}>
                                    Add Bar
                                </Button>
    
                                <Modal show={show} onHide={this.handleClose} centered>
                                    <Modal.Header>
                                        <Modal.Title>Add Bar</Modal.Title>
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
                            {barsList}
                        </div>
                    </Row>
                </Fragment>
            );
        }
    }
}

export default Bars;
