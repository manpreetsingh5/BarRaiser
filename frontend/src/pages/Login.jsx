import React, { Fragment, Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import style from '../style/Login.module.css';

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form';


class Login extends Component {
    constructor(props) {
        super(props);
    
        this.state = {
            showSuccessMessage: false,
            hasLoginFailed: false,
            validCredentials: true
        };
    }

    handleSubmit = event => {
        let form = event.target;
        
        const acc = {
            email: form.elements.email.value,
            password: form.elements.password.value,
        }
        console.log(JSON.stringify(acc));

        fetch('api/auth/signin', {
            method: 'POST',
            body: JSON.stringify(acc),
            headers: {
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
                this.setState({
                    validCredentials: false
                });
            }
            else {
                console.log(data)
                this.props.authorize(data);
            }
        })

        event.preventDefault();
    }

    render() {
        let validCredentials = this.state.validCredentials;
        let invalidMessage = "";
        if(!validCredentials) invalidMessage = "Invalid credentials.";

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
                                            <h6 className={style.invalid}>{invalidMessage}</h6>
                                        </div>

                                        {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                                        {this.state.showSuccessMessage && <div>Login Sucessful</div>}

                                        <Form onSubmit={this.handleSubmit}>
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

                                            <div className={style.buttonsDiv}>
                                                <Button 
                                                    type="submit"
                                                    className={`${style.login} ${style.btn}`}
                                                >
                                                    Sign in
                                                </Button>

                                                <Link to="/register">
                                                    <p className={style.register}>Register</p>
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

export default Login;
