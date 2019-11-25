import React, { Fragment, Component } from 'react';
import StepCards from './StepCards'
import Empty from './Empty';
import Load from './Load';
import style from '../style/Drinks.module.css';

import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image';
import Table from 'react-bootstrap/Table';


class Drinks extends Component {
    constructor(props) {
        super(props);

        this.state = {
            show: false,
            drinks: [],
            isLoaded: false,
            steps: [],
            stepObjects: [],
            games: [],
            ingredients: [],
            equipment: []
        };
    }

    componentDidMount() {
        let token = localStorage.getItem("accessToken");
        fetch(`api/drink/viewAll`, {
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
            return response.json()
            .then(
                data => {
                    if(data !== null) {
                        this.setState({
                            drinks: data
                        })
                        // return data;
                    }
                    // return null;
                }
            )
        })
        .then(() => {
            fetch(`api/equipment/viewAllEquipment`, {
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
                                equipment: data
                            })
                        }
                    }
                )
            })
        })
        .then(() => {
            fetch(`api/equipment/viewAllIngredients`, {
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
                                ingredients: data
                            })
                        }
                    }
                )
            })
        })
        .then(() => {
            fetch(`api/actions/getActions`, {
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
                                games: data,
                                isLoaded: true
                            })
                        }
                    }
                )
            })
        })
    }

    callback = (drinks) => {
        this.setState({
            drinks: drinks,
        })
    }

    handleSubmit = (event) => {
        console.log("submitted")
    }

    handleShow = () => {this.setState({show: true})}

    handleClose = () => {this.setState({show: false})}

    addStep = () => {
        this.setState({
            steps: [
                ...this.state.steps,
                <StepCards ingredients={this.state.ingredients} equipment={this.state.equipment} games={this.state.games} add={this.addToStepObjects}/>
            ]
        });
    }

    addToStepObjects = (obj) => {
        console.log("lol")
    }

    render() {
        let show = this.state.show;
        let empty = null;
        let isLoaded = this.state.isLoaded;
        let barsList = []
        let ingredients = this.state.ingredients;
        let equipment = this.state.equipment;
        let games = this.state.games

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
                            <h3>Drinks</h3>
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
                                <h3>Drinks</h3>
                            </div>
                        </Row>
        
                        <Row className={style.contentContainer}>
                            <div className={style.contentDiv}>
                                <div>
                                    <h4>Your Drinks</h4>
                                </div>
        
                                <div>
                                    <Button variant="primary" onClick={this.handleShow}>
                                        Add Drinks
                                    </Button>
        
                                    <Modal show={show} onHide={this.handleClose} centered dialogClassName={style.modal}>
                                        <Modal.Header>
                                            <Modal.Title>Add Drink</Modal.Title>
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
    
                                                {this.state.steps}
    
                                                <Button onClick={this.addStep}>
                                                    Add Step
                                                </Button>
        
                                                <Modal.Footer>
                                                    <Button variant="secondary" onClick={this.handleClose}>
                                                        Close
                                                    </Button>
        
                                                    <Button variant="primary" type="submit">
                                                        Add Drink
                                                    </Button>
                                                </Modal.Footer>
                                            </Form>
                                        </Modal.Body>
                                    </Modal>
                                </div>
                                {empty}
                            </div>
                        </Row>
                </Fragment>
            );
        }
    }
}

export default Drinks;
