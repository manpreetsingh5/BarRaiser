import React, { Fragment, Component } from 'react';
import { Link, Redirect, withRouter } from "react-router-dom";
import style from '../style/Register.module.css';

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form';


class Register extends Component {
    constructor(props) {
        super(props);
    
        this.state = {
            validCredentials: true,
            accepted: false
        };
    }

    handleSubmit = event => {
        let form = event.target;
        let typeval;

        if(form.elements.type[0].checked === true) {
            typeval = form.elements.type[0].id
        }
        else {
            typeval = form.elements.type[1].id
        }
        
        const acc = {
            first_name: form.elements.firstName.value,
            last_name: form.elements.lastName.value,
            email: form.elements.email.value,
            password: form.elements.password.value,
            status: typeval
        }
        console.log(JSON.stringify(acc));

        fetch('api/auth/signup', {
            method: 'POST',
            body: JSON.stringify(acc),
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => {
            console.log(response.status)
            if(response.status !== 201) {
                return null;
            }
            return response;
        })
        .then(data => {
            if(data === null) {
                this.setState({
                    validCredentials: false
                });
            }
            else {
                this.setState({
                    validCredentials: true,
                    accepted: true
                })
            }
        })

        event.preventDefault();
    }

    handleChange = () => {}

    render() {
        let accepted = this.state.accepted;
        let validCredentials = this.state.validCredentials;
        let validMessage = "";
        let invalidMessage = "";
        if(this.state.accepted) validMessage = "Account successfully created! Please return to the login page to login."
        if(!validCredentials) invalidMessage = "An account with that email has already been taken.";

        if(accepted) {
            return (
                <Redirect
                    to={"/login"}
                />
            )
        }

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
                                            <h6 className={style.valid}>{validMessage}</h6>
                                            <h6 className={style.invalid}>{invalidMessage}</h6>
                                        </div>

                                        <Form onSubmit={this.handleSubmit}>
                                            <Form.Group controlId="firstName">
                                                <Form.Label>First Name</Form.Label>

                                                <Form.Control 
                                                    required
                                                    type="firstName" 
                                                    placeholder="Enter first name" 
                                                />
                                            </Form.Group>
                                            <Form.Group controlId="lastName">
                                                <Form.Label>Last Name</Form.Label>

                                                <Form.Control 
                                                    required
                                                    type="lastName" 
                                                    placeholder="Enter last name" 
                                                />

                                            </Form.Group>
                                            <Form.Group controlId="email">
                                                <Form.Label>Email address</Form.Label>

                                                <Form.Control 
                                                    required
                                                    type="email" 
                                                    placeholder="Enter email" 
                                                />

                                            </Form.Group>

                                            <Form.Group controlId="password">
                                                <Form.Label>Password</Form.Label>
                                                <Form.Control 
                                                    required
                                                    type="password" 
                                                    placeholder="Enter password" 
                                                />
                                            </Form.Group>

                                            <Form.Group controlId="type">
                                                <Form.Check id="TRAINEE" type="radio" onChange={this.handleChange} name="type" label="Trainee" inline checked/>
                                                <Form.Check id="BARTENDER" type="radio" name="type" onChange={this.handleChange} label="Bartender" inline />
                                            </Form.Group>
                                            

                                            <div className={style.buttonsDiv}>
                                                <Button 
                                                    type="submit"
                                                    className={`${style.login} ${style.btn}`}
                                                >
                                                    Register
                                                </Button>

                                                <Link to="/login">
                                                    <p className={style.register}>Return to login</p>
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

export default withRouter(Register);
