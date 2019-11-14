import React, { Fragment, Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch, Redirect } from "react-router-dom";
import AuthenticationService from '../AuthenticationService';
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
        };
    }

    componentDidMount() {
        // fetch('api/hello', {
        //     method: 'GET',
        //     headers: {
        //         'Content-Type': 'application/json',
        //     }
        // })
        // .then(response => {
        //     if(response.status !== 200) {
        //         return null;
        //     }
        //     return response.json();
        // })
        // .then(data => {
        //     console.log(data);
        // })
    }

    handleSubmit = event => {
        let form = event.target
        
        const acc = {
            email: form.elements.email.value,
            password: form.elements.password.value
        }

        console.log(acc)

        // AuthenticationService.registerSuccessfulLogin(acc.email, acc.password)
        // .then(() => {
        //     this.props.history.push(`/courses`)
        // }).catch(() => {
        //     this.setState({ showSuccessMessage: false })
        //     this.setState({ hasLoginFailed: true })
        // })
        event.preventDefault();
    }

    render() {
        console.log(this.state.showSuccessMessage, this.state.hasLoginFailed)
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
                                                    <p className={style.register} onClick={this.test}>Register</p>
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
