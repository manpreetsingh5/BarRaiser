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

        this.props.games.sort((a, b) => (a.action.name > b.action.name) ? 1 : -1)
        this.props.ingredients.sort((a, b) => (a.equipment.name > b.equipment.name) ? 1 : -1)
        this.props.equipment.sort((a, b) => (a.equipment.name > b.equipment.name) ? 1 : -1)
        this.props.units.sort((a, b) => (a > b) ? 1 : -1)

        if(this.props.action != null && this.props.description != null && this.props.equipmentSet != null) {
            console.log("hello")
            let actions = [];
            this.props.games.forEach((el) => {
                actions.push(el.action.name);
            })

            let ingredients = [];
            this.props.ingredients.forEach((el) => {
                ingredients.push(el.equipment.name);
            })

            let equipments = [];
            this.props.equipment.forEach((el) => {
                equipments.push(el.equipment.name);
            })

            let ingredient = null;
            let equipment = null;
            this.props.equipmentSet.forEach((el, index) => {
                if(el.equipment.type === "INGREDIENT") {
                    ingredient = el;
                }
                else if(el.equipment.type === "EQUIPMENT") {
                    equipment = el;
                }
            })

            let actionIndex = actions.indexOf(this.props.action);
            let action = this.props.games[actionIndex];

            let ingredientIndex = ingredients.indexOf(ingredient.equipment.name);
            let equipmentIndex = equipments.indexOf(equipment.equipment.name);

            let ingredientFile = this.props.ingredients[ingredientIndex];
            let equipmentFile = this.props.equipment[equipmentIndex];

            // console.log(ingredient);

            if(ingredient != null && equipment == null) {
                let unit = ingredient.unit;
                let unitIndex = this.props.units.indexOf(unit)
                this.state = {
                    actionPic: action.file,
                    ingredientPic: ingredientFile.file,
                    equipmentPic: null,
                    selectedAction: actionIndex,
                    selectedIngredient: ingredientIndex,
                    selectedEquipment: 0,
                    selectedUnit: unitIndex,
                    quantity: ingredient.quantity,
                };
            }
            else if(ingredient == null && equipment != null) {
                let unit = equipment.unit;
                let unitIndex = this.props.units.indexOf(unit)
                // console.log(unitIndex)
                this.state = {
                    actionPic: action.file,
                    ingredientPic: null,
                    equipmentPic: equipmentFile.file,
                    selectedAction: actionIndex,
                    selectedIngredient: 0,
                    selectedEquipment: equipmentIndex,
                    selectedUnit: unitIndex,
                    quantity: equipment.quantity,
                };
            }
            else {
                let unit = ingredient.unit;
                let unitIndex = this.props.units.indexOf(unit)
                // console.log(unitIndex)
                this.state = {
                    actionPic: action.file,
                    ingredientPic: ingredientFile.file,
                    equipmentPic: equipmentFile.file,
                    selectedAction: actionIndex,
                    selectedIngredient: ingredientIndex,
                    selectedEquipment: equipmentIndex,
                    selectedUnit: unitIndex,
                    quantity: ingredient.quantity,
                };
            }
        }
        else {
            this.state = {
                actionPic: this.props.games[0].file,
                ingredientPic: null,
                equipmentPic: null,
                selectedAction: 0,
                selectedIngredient: 0,
                selectedEquipment: 0,
                selectedUnit: 0,
                quantity: null,
            };
        }
    }

    handleSelectIngredient = (event) => {
        if(event.target.value != -1) {
            let ingredient = this.props.ingredients[event.target.value];
            this.setState({
                ingredientPic: ingredient.file,
            })
        }
        else {
            this.setState({
                ingredientPic: null,
            })
        }
    }

    handleSelectEquipment = (event) => {
        if(event.target.value != -1) {
            let equipment = this.props.equipment[event.target.value]
            this.setState({
                equipmentPic: equipment.file,
            })
        }
        else {
            this.setState({
                equipmentPic: null,
            })
        }
    }

    handleSelectAction = (event) => {
        let action = this.props.games[event.target.value];
        this.setState({
            actionPic: action.file,
            selectedAction: action.action.name
        })
    }

    render() {
        let actionList = [];
        let ingredientList = [
            <option key={`None`} value={-1} id={-1}>None</option>
        ];
        let equipmentList = [
            <option key={`None`} value={-1} id={-1}>None</option>
        ];
        let actions = this.props.games;
        let units = this.props.units
        let ingredients = this.props.ingredients;
        let equipment = this.props.equipment;
        let actionPic = this.state.actionPic;
        let ingredientPic = this.state.ingredientPic;
        let equipmentPic = this.state.equipmentPic;
        let image, ingredientImage, equipmentImage;
        let unitList = []
        console.log(this.state.selectedUnit)

        if(actionPic) {
            image = (
                <Row className={style.centerContent}>
                    <Col sm={8}>
                        <Image src={`data:image/png;base64,${actionPic}`} fluid />
                    </Col>
                </Row>
            )
        }
        else {
            image = null;
        }

        if(ingredientPic) {
            ingredientImage = (
                <Row className={style.centerContent}>
                    <Col sm={2} className={style.centerContent}>
                        <Image src={`data:image/png;base64,${ingredientPic}`} fluid />
                    </Col>
                </Row>
            )
        }
        else {
            ingredientImage = null;
        }

        if(equipmentPic) {
            equipmentImage = (
                <Row className={style.centerContent}>
                    <Col sm={2} className={style.centerContent}>
                        <Image src={`data:image/png;base64,${equipmentPic}`} fluid />
                    </Col>
                </Row>
            )
        }
        else {
            equipmentImage = null;
        }

        actions.forEach((el, index) => {
            actionList.push(
                <option key={`${el.action.name}${index}`} value={index}>{el.action.name}</option>
            )
        })

        units.forEach((el, index) => {
            unitList.push(
                <option key={`${el}${index}`} value={index}>{el}</option>
            )
        })

        ingredients.forEach((el, index) => {
            ingredientList.push(
                <option key={`${el.equipment.name}${index}`} value={index} id={el.equipment.id}>{el.equipment.name}</option>
            )
        })

        equipment.forEach((el, index) => {
            equipmentList.push(
                <option key={`${el.equipment.name}${index}`} value={index} id={el.equipment.id}>{el.equipment.name}</option>
            )
        })

        if(this.props.action != null && this.props.description != null && this.props.equipmentSet != null) {
            // console.log(this.state.selectedUnit)

            return (
                <Fragment>
                    <Card className={style.card}>
                        <Card.Body>
                            <Form.Group controlId={`stepDescription${this.props.id}`}>
                                <Form.Label>Step Description</Form.Label>
                                <Form.Control 
                                    required
                                    as="textarea"
                                    defaultValue={this.props.description}
                                    placeholder="Enter description" 
                                />
                            </Form.Group>
                            
                            <Form.Group controlId={`action${this.props.id}`}>
                                <Form.Label>Action</Form.Label>
                                <Form.Control 
                                    as="select" 
                                    onChange={this.handleSelectAction}
                                    defaultValue={this.state.selectedAction}
                                >
                                {actionList}
                                </Form.Control>
                            </Form.Group>
                            {image}
    
                            <Form.Group controlId={`ingredient${this.props.id}`}>
                                <Form.Label>Ingredient</Form.Label>
                                <Form.Control 
                                    as="select" 
                                    onChange={this.handleSelectIngredient}
                                    defaultValue={this.state.selectedIngredient}
                                >
                                {ingredientList}
                                </Form.Control>
                            </Form.Group>
                            {ingredientImage}
    
                            <Form.Group controlId={`equipment${this.props.id}`}>
                                <Form.Label>Equipment</Form.Label>
                                <Form.Control 
                                    as="select" 
                                    onChange={this.handleSelectEquipment}
                                    defaultValue={this.state.selectedEquipment}
                                >
                                {equipmentList}
                                </Form.Control>
                            </Form.Group>
                            {equipmentImage}
    
                            <Form.Group controlId={`amount${this.props.id}`}>
                                <Form.Label>Amount</Form.Label>
    
                                <Form.Control 
                                    required
                                    type="number"
                                    min="1"
                                    max="10" 
                                    placeholder="Enter amount"
                                    defaultValue={this.state.quantity}
                                />
                            </Form.Group>
                            <Form.Group controlId={`unit${this.props.id}`}>
                                <Form.Label>Unit</Form.Label>
                                <Form.Control as="select" defaultValue={this.state.selectedUnit}>
                                {unitList}
                                </Form.Control>
                            </Form.Group>
                        </Card.Body>
                    </Card>
                </Fragment>
            );
        }

        return (
            <Fragment>
                <Card className={style.card}>
                    <Card.Body>
                        <Form.Group controlId={`stepDescription${this.props.id}`}>
                            <Form.Label>Step Description</Form.Label>
                            <Form.Control 
                                required
                                as="textarea"
                                placeholder="Enter description" 
                            />
                        </Form.Group>
                        
                        <Form.Group controlId={`action${this.props.id}`}>
                            <Form.Label>Action</Form.Label>
                            <Form.Control as="select" onChange={this.handleSelectAction}>
                            {actionList}
                            </Form.Control>
                        </Form.Group>
                        {image}

                        <Form.Group controlId={`ingredient${this.props.id}`}>
                            <Form.Label>Ingredient</Form.Label>
                            <Form.Control as="select" onChange={this.handleSelectIngredient}>
                            {ingredientList}
                            </Form.Control>
                        </Form.Group>
                        {ingredientImage}

                        <Form.Group controlId={`equipment${this.props.id}`}>
                            <Form.Label>Equipment</Form.Label>
                            <Form.Control as="select" onChange={this.handleSelectEquipment}>
                            {equipmentList}
                            </Form.Control>
                        </Form.Group>
                        {equipmentImage}

                        <Form.Group controlId={`amount${this.props.id}`}>
                            <Form.Label>Amount</Form.Label>

                            <Form.Control 
                                required
                                type="number"
                                min="1"
                                max="10" 
                                placeholder="Enter amount" 
                            />
                        </Form.Group>
                        <Form.Group controlId={`unit${this.props.id}`}>
                            <Form.Label>Unit</Form.Label>
                            <Form.Control as="select">
                            {unitList}
                            </Form.Control>
                        </Form.Group>
                    </Card.Body>
                </Card>
            </Fragment>
        );
    }
}

export default StepCards;
