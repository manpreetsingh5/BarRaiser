import React, { Fragment, Component } from 'react';
import style from '../style/StepCards.module.css';

import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image';
import Table from 'react-bootstrap/Table';
import Dropdown from 'react-bootstrap/Dropdown';


class StepCards extends Component {
    constructor(props) {
        super(props);

        this.state = {
            actionPic: null,
            selectedAction: null,
            selectedIngredient: null,
            selectedEquipment: null
        };
    }

    handleSelectAction = (action) => {
        this.setState({
            actionPic: action.file,
            selectedAction: action.action.name
        })
    }

    handleSelectIngredient = (ingredient) => {
        this.setState({
            selectedIngredient: ingredient.equipment.name
        })
    }

    handleSelectEquipment = (equipment) => {
        this.setState({
            selectedEquipment: equipment.equipment.name
        })
    }

    render() {
        let actionList = [];
        let ingredientList = [];
        let equipmentList = [];
        let actions = this.props.games;
        let ingredients = this.props.ingredients;
        let equipment = this.props.equipment;

        actions.forEach((el, index) => {
            actionList.push(
                <Dropdown.Item key={`${el.action.name}${index}`} onClick={() => this.handleSelectAction(el)}>{el.action.name}</Dropdown.Item>
            )
        })

        ingredients.forEach((el, index) => {
            ingredientList.push(
                <Dropdown.Item key={`${el.equipment.name}${index}`} onClick={() => this.handleSelectIngredient(el)}>{el.equipment.name}</Dropdown.Item>
            )
        })

        equipment.forEach((el, index) => {
            equipmentList.push(
                <Dropdown.Item key={`${el.equipment.name}${index}`} onClick={() => this.handleSelectEquipment(el)}>{el.equipment.name}</Dropdown.Item>
            )
        })


        console.log(this.props)
        return (
            <Fragment>
                <Card className={style.card}>
                    <Card.Body>
                        <Dropdown>
                            <Dropdown.Toggle variant="success" id="dropdown-basic">
                                Select Action
                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                {actionList}
                            </Dropdown.Menu>
                        </Dropdown>
                        <h5>{this.state.selectedAction}</h5>
                        <Image src={`data:image/png;base64,${this.state.actionPic}`} fluid />
                        <Dropdown>
                            <Dropdown.Toggle variant="success" id="dropdown-basic">
                                Select Ingredient
                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                {ingredientList}
                            </Dropdown.Menu>
                        </Dropdown>
                        <h6>{this.state.selectedIngredient}</h6>
                        <Dropdown>
                            <Dropdown.Toggle variant="success" id="dropdown-basic">
                                Select equipment
                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                {equipmentList}
                            </Dropdown.Menu>
                        </Dropdown>
                        <h6>{this.state.selectedEquipment}</h6>
                    </Card.Body>
                </Card>
            </Fragment>
        );
    }
}

export default StepCards;
