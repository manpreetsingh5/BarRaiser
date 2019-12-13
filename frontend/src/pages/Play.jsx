import React, { Fragment, Component } from 'react';
import { PourLiquidGame } from './Games'
import Empty from './Empty';
import Load from './Load';
import style from '../style/Play.module.css';

import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image';
import Table from 'react-bootstrap/Table';

import { FaWineBottle } from 'react-icons/fa';

class Play extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isLoaded: false,
            step: null,
        }
    }

    componentDidMount() {
        this.setState({
            isLoaded: true,
            step: this.props.drink.steps[0],
        })
    }

    render() {
        let isLoaded = this.state.isLoaded
        let step = this.state.step;
        let game = null

        console.log(step)

        if(step) {
            if(step.action == "POURING_LIQUID") {
                game = <PourLiquidGame unit={"oz"} />
            }
            else if(step.action == "POURING_SOLID") {

            }
            else if(step.action == "SHAKE") {

            }
            else if(step.action == "PUT") {

            }
            else if(step.action == "MATCH") {

            }
            else if(step.action == "ROLL") {

            }
            else if(step.action == "STIR") {

            }
            else if(step.action == "STRAIN") {

            }
        }

        // console.log(game)

        if(!isLoaded) {
            return (
                <Fragment>
                    <Row className={style.titleContainer}>
                        <div className={style.titleDiv}>
                            <h3>Play</h3>
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
                            <h3>Play</h3>
                        </div>
                    </Row>
                    {game}
                </Fragment>
            );
        }
    }
}

export default Play;
