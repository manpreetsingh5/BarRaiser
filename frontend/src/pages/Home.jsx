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
                                        <li>Select from our fun yet effective training simulations to help teach your trainees how to properly make your drink!</li>
                                    </ol>
                                    <hr></hr>
                                    <p>Select <strong>Bars</strong> to create or edit your own bar.</p>
                                    <ol>
                                        <li>Customize your bar's title, description, and display image.</li>
                                        <li>Add trainees directly to your bar using their email address</li>
                                        <li>Assign your drink to your trainees.</li>
                                        <li>Keep track of your trainees' drink completion.</li>
                                    </ol>
                                    <hr></hr>
                                    <p>BarRaiser provides a variety of fun, interactive, simulations to ensure your students learn how to make the best drinks
                                    in town! They include: </p>
                                    <ol>
                                        <li><em>Pouring liquids:</em> In this simulation, your trainee can simulate pouring the most exquisite whiskys time and time again to perfect
                                            their skills. In this simulation, a bartender <em>must</em> upload an ingredient and an equipment, and BarRaiser will
                                            do the rest.</li>
                                        <li><em>Pouring solids:</em> Similar to pouring a liquid, your trainee can simulate pouring ingredients such as salt. In this simulation
                                            the bartender must again upload an ingredient and an equipment for the trainee to simulate.</li>
                                        <li><em>Shaking:</em> In this simulation, a trainee can simulate shaking a martini, just like James Bond would want! However, the bartender must
                                        be sure to upload the equipment so the trainee can simulate shaking.</li>
                                        <li><em>Straining:</em> In this simulation, a trainee can simulate the proper technique of straining any cocktail they please. The bartender must
                                            upload an equipment and an ingredient to strain, and BarRaiser will ensure they become experts! </li>
                                        <li><em>Fill Ingredient:</em> In this simulation, a trainee can simulate filling their cocktails with the finest ingredients. Once the instructor
                                        uploads an ingredient and the appropriate equipment, the trainee can simulate adding an ingredient to the equipment of your choice. </li>
                                        <li><em>Stirring:</em> In this simulation, a trainee can simulate stirring a cocktail. The instructor must be sure to upload the appropriate
                                        equipment for this simulation to work. </li>
                                        <li><em>Rolling:</em> In this simulation, a trainee can simulate rolling. The instructor must be sure to upload the appropriate equipment
                                        for this simulation to work.</li>
                                        <li><em>Matching:</em> In this simulation, a trainee can simulate matching two ingredients or equipments to sharpen their memorization skills.
                                            The instructor must be sure to upload the appropriate equipment or ingredient for this simulation to work.</li>
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
