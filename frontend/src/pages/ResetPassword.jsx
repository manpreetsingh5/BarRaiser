import React, { Fragment, Component } from 'react';
import { Link, Redirect, withRouter } from "react-router-dom";
import style from '../style/Register.module.css';

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form';


class ResetPassword extends Component {
    constructor(props) {
        super(props);
    
        this.state = {
            validCredentials: true,
            accepted: false
        };
    }

    handleSubmit = event => {
        let form = event.target;
        let email = form.elements.email.value;
        fetch(`api/auth/requestPasswordReset?email=${email}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => {
            console.log(response)
            if(response.status !== 200) {
                return null;
            }
            return response;
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
                                            <p className={style.subtitle}>Enter your email to reset your password.</p>
                                            <h6 className={style.valid}>{validMessage}</h6>
                                            <h6 className={style.invalid}>{invalidMessage}</h6>
                                        </div>

                                        <Form onSubmit={this.handleSubmit}>
                                            <Form.Group controlId="email">
                                                <Form.Label>Email address</Form.Label>

                                                <Form.Control 
                                                    required
                                                    type="email" 
                                                    placeholder="Enter email" 
                                                />

                                            </Form.Group>

                                            <div className={style.buttonsDiv}>
                                                <Button 
                                                    type="submit"
                                                    className={`${style.login} ${style.btn}`}
                                                >
                                                    Send Reset Link
                                                </Button>

                                                <Link to="/login">
                                                    <p className={`${style.register} ${'my-2'}`}>Return to login</p>
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

export default withRouter(ResetPassword);