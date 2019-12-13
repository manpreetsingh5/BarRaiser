import React, { Fragment, Component } from 'react';
import style from '../style/Home.module.css';

import Row from 'react-bootstrap/Row';
import {Col, Card} from 'react-bootstrap';
import beer_src from '../img/beer-home.png';

class TraineeHome extends Component {
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
                                    <p>Select <strong>Drinks</strong> to view drinks. </p>
                                    <ol>
                                        <li>View and learn how to make publicly available drinks.</li>
                                        <li>View and complete your assigned drinks.</li>
                                        <li>Learn by playing our fun yet effective games!</li>
                                    </ol>
                                    <hr></hr>
                                    <p>Select <strong>Bars</strong> to view your bar details.</p>
                                    <ol>
                                        <li>View your bar's title, description, and display image.</li>
                                        <li>View the owner of your respective bars.</li>
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

export default TraineeHome;
