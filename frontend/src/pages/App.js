import React, { Fragment, Component } from 'react';
import logo from '../logo.svg';
import style from '../style/App.module.css';

import img1 from '../img/1.jpg'

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form';


class App extends Component {
    handleSubmit = () => {

    }
    handleChange = () => {

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
                                <h1>BarRaiser</h1>
                                <Form onSubmit={this.handleSubmit}>
                                <Form.Group controlId="email">
                                    <Form.Label>Email address</Form.Label>

                                    <Form.Control 
                                        required
                                        type="email" 
                                        placeholder="Enter email" 
                                        // value={this.state.email}
                                        onChange={this.handleChange}
                                    />

                                    <Form.Text className="text-muted">
                                        We'll never share your email with anyone else (Unless we're hacked).
                                    </Form.Text>
                                </Form.Group>

                                <Form.Group controlId="password">
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control 
                                        required
                                        type="password" 
                                        placeholder="Password" 
                                        // value={this.state.password}
                                        onChange={this.handleChange}
                                    />
                                </Form.Group>

                                <div>
                                    <Button 
                                        type="submit"
                                        // className={`${styles.login} ${styles.btn}`}
                                    >
                                        Login
                                    </Button>

                                    {/* <Link to="/createaccount"> */}
                                        <Button 
                                            type="submit"
                                            // className={`${styles.createAcc} ${styles.btn}`}
                                        >
                                            Create Account
                                        </Button>
                                    {/* </Link> */}
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

export default App;
