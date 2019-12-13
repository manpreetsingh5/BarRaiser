import React, { Fragment, Component } from 'react';
import style from '../style/Home.module.css';

import Row from 'react-bootstrap/Row'
import { Container, Card, Col } from 'react-bootstrap';
import beer_src from '../img/beer-home.png';

class Home extends Component {
    render() {
        return (
            <Fragment>
                <Row>
                    <div className={style.titleDiv}>
                        <h3>Home</h3>
                    </div>
                </Row>

                <Row className="my-4">
                    <Col md={8} className="mx-auto">
                        <Card className={style.cardShadow}>
                            <Card.Body>
                                <Card.Title className="text-center">Welcome to BarRaiser</Card.Title>
                                <Card.Text>
                                    <Row>
                                        <Col md={4} className="mx-auto">
                                            <img className="img-fluid" src={beer_src} />
                                        </Col>
                                    </Row>
                                    <hr></hr>
                                    <p>Select <strong>Drinks</strong> to upload your own drink, ingredient, or equipment. </p>
                                    <ol>
                                        <li>Upload <em>any</em> ingredient or equipment using <em>any</em> jpg or png.</li>
                                        <li>Customize your drink's title, description, and display image.</li>
                                        <li>Select from our fun yet effective training games to help teach your trainees how to properly make your drink!</li>
                                    </ol>
                                    <hr></hr>
                                    <p>Select <strong>Bars</strong> to create or edit your own bar.</p>
                                    <ol>
                                        <li>Customize your bar's title, description, and display image.</li>
                                        <li>Add trainees directly to your bar using their email address</li>
                                        <li>Assign your drink to your trainees.</li>
                                        <li>Keep track of your trainees' drink completion.</li>
                                    </ol>
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Fragment>
                );
            }
        }
        
        export default Home;
