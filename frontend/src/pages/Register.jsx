import React, { Fragment, Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch, Redirect } from "react-router-dom";
import style from '../style/Register.module.css';

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form';


class Register extends Component {
    handleSubmit = () => {

    }

    handleChange = () => {

    }

    test = () => {
        console.log("test");
    }

    render() {
        return (
            <Fragment>
                <Row className={style.cardWrapper}>
                    <Col md="auto">
                        <Card className={style.card}>
                            <Card.Body className={style.cardBody}>
                                <div className={style.wrapper}>
                                    <div className={style.imgWrapper} />

                                    <div className={style.loginWrapper} >
                                        <div className={style.titleDiv}>
                                            <h1>BarRaiser</h1>
                                            <p className={style.subtitle}>raising the bar of bartending, one click at a time</p>
                                        </div>

                                        <Form onSubmit={this.handleSubmit}>
                                            <Form.Group controlId="firstName">
                                                <Form.Label>First Name</Form.Label>

                                                <Form.Control 
                                                    required
                                                    type="firstName" 
                                                    placeholder="Enter first name" 
                                                    // value={this.state.email}
                                                    onChange={this.handleChange}
                                                />
                                            </Form.Group>
                                            <Form.Group controlId="lastName">
                                                <Form.Label>Last Name</Form.Label>

                                                <Form.Control 
                                                    required
                                                    type="lastName" 
                                                    placeholder="Enter last name" 
                                                    // value={this.state.email}
                                                    onChange={this.handleChange}
                                                />

                                            </Form.Group>
                                            <Form.Group controlId="email">
                                                <Form.Label>Email address</Form.Label>

                                                <Form.Control 
                                                    required
                                                    type="email" 
                                                    placeholder="Enter email" 
                                                    // value={this.state.email}
                                                    onChange={this.handleChange}
                                                />

                                            </Form.Group>

                                            <Form.Group controlId="password">
                                                <Form.Label>Password</Form.Label>
                                                <Form.Control 
                                                    required
                                                    type="password" 
                                                    placeholder="Enter password" 
                                                    // value={this.state.password}
                                                    onChange={this.handleChange}
                                                />
                                            </Form.Group>

                                            <Form.Group controldId="type">
                                                <Form.Check type="radio" name="type" label="Trainee" inline checked/>
                                                <Form.Check type="radio" name="type" label="Professional" inline />
                                            </Form.Group>
                                            

                                            <div className={style.buttonsDiv}>
                                                <Button 
                                                    type="submit"
                                                    className={`${style.login} ${style.btn}`}
                                                >
                                                    Register
                                                </Button>

                                                <Link to="/login">
                                                    <p className={style.register} onClick={this.test}>Return to login</p>
                                                </Link>
                                            </div>
                                        </Form>
                                    </div>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Fragment>
        );
    }
}

export default Register;
