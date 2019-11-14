import React, { Fragment, Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch, Redirect } from "react-router-dom";
import style from '../style/Drinks.module.css';

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form';
import ButtonGroup from 'react-bootstrap/ButtonGroup';

import {FaClipboard} from "react-icons/fa";
import {FaGlassWhiskey} from "react-icons/fa";
import {FaUsers} from "react-icons/fa";
import {FaQuestionCircle} from "react-icons/fa";
import {FaSignOutAlt} from "react-icons/fa";


class Drinks extends Component {
    render() {
        return (
            <Fragment>
                <Row className={style.container}>
                    <div>
                        <Button>
                            Add Drink
                        </Button>
                    </div>
                </Row>
            </Fragment>
        );
    }
}

export default Drinks;
