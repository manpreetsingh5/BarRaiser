import React, { Fragment, Component } from 'react';
import Empty from './Empty';
import style from '../style/Drinks.module.css';

import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image';
import Table from 'react-bootstrap/Table';


class Drinks extends Component {
    constructor(props) {
        super(props);

        this.state = {
            show: false,
            drinks: [],
            isLoaded: false
        };
    }

    componentDidMount() {

    }

    handleShow = () => {this.setState({show: true})}

    handleClose = () => {this.setState({show: false})}

    render() {
        let show = this.state.show;
        let empty = null;

        return (
            <Fragment>
                <Row className={style.titleContainer}>
                        <div className={style.titleDiv}>
                            <h3>Drinks</h3>
                        </div>
                    </Row>
    
                    <Row className={style.contentContainer}>
                        <div className={style.contentDiv}>
                            <div>
                                <h4>Your Drinks</h4>
                            </div>
    
                            <div>
                            </div>
                            {empty}
                        </div>
                    </Row>
            </Fragment>
        );
    }
}

export default Drinks;
