import React, { Fragment, Component } from 'react';
import { PourLiquidGame, PourSolidGame, ShakeGame, FillGame, MatchingGame, RollGame, StirGame, StrainGame } from './Games'
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
            ingredientPic: null,
            equipmentPic: null,
            unit: null,
            target: null,
            allIngredients: null,
        }
    }

    componentDidMount() {
        let token = localStorage.getItem("accessToken")
        this.props.drink.steps.sort((a, b) => (a.step_number > b.step_number) ? 1 : -1)
        let length = this.props.drink.steps[0].equipmentSet.length;
        let arr = [];
        let processed = 0
        this.props.drink.steps[0].equipmentSet.sort((a, b) => (a.equipment.type > b.equipment.type) ? -1 : 1)

        fetch(`api/equipment/viewAllIngredientsAndEquipment`, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer '+ token,
                'Content-Type': 'application/json',
            }
        })
        .then(response => {
            // console.log(response)
            if(response.status !== 200) {
                return null;
            }
            return response.json().then(data => {
                let arr = [];
                data.forEach(el => {
                    arr.push(el.file);
                })
                this.setState({
                    allIngredients: arr
                })
            })
        })

        this.props.drink.steps[0].equipmentSet.forEach((el) => {
            fetch(`api/equipment/viewPicture?image_path=${el.equipment.image_path}`, {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer '+ token,
                    'Content-Type': 'application/json',
                }
            })
            .then(response => {
                if(response.status !== 200) {
                    return null;
                }
                return response.json().then(data =>{
                    if(data === null) {
                        console.log("oops");
                    }
                    else {
                        // console.log(data)
                        arr.push({
                            type: el.equipment.type,
                            file: data.file
                        })
                        processed += 1;
                        // console.log(processed)
                        if(processed == length) {
                            this.callback(arr, el)
                        }
                    }
                });
            })
        })
    }

    handleNext = () => {
        this.setState({isLoaded: false})
        if(!this.props.drink.steps.length) {
            let token = localStorage.getItem("accessToken")
            fetch(`api/progress/addProgress?cohort_id=${this.props.cohortId}&drink_id=${this.props.drinkId}&user_id=${this.props.id}`, {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer '+ token,
                    'Content-Type': 'application/json',
                }
            })
            .then(response => {
                console.log(response);
            })
            this.props.handleComplete();
        }

        else {
            let token = localStorage.getItem("accessToken")
            this.props.drink.steps.sort((a, b) => (a.step_number > b.step_number) ? 1 : -1)
            let length = this.props.drink.steps[0].equipmentSet.length;
            let arr = [];
            let processed = 0
            this.props.drink.steps[0].equipmentSet.sort((a, b) => (a.equipment.type > b.equipment.type) ? -1 : 1)

            this.props.drink.steps[0].equipmentSet.forEach((el) => {
                fetch(`api/equipment/viewPicture?image_path=${el.equipment.image_path}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer '+ token,
                        'Content-Type': 'application/json',
                    }
                })
                .then(response => {
                    if(response.status !== 200) {
                        return null;
                    }
                    return response.json().then(data =>{
                        if(data === null) {
                            console.log("oops");
                        }
                        else {
                            arr.push({
                                type: el.equipment.type,
                                file: data.file
                            })
                            processed += 1;
                            if(processed == length) {
                                this.callback(arr, el)
                            }
                        }
                    });
                })   
            })
        }
    }

    callback = (arr, equipmentSet) => {
        arr.sort((a, b) => (a.type > b.type) ? -1 : 1)
        console.log(arr);
        this.setState({step: null})
        setTimeout(() => {
            this.setState({
                isLoaded: true,
                step: this.props.drink.steps[0],
                ingredientPic: arr[0].file,
                equipmentPic: arr[1].file,
                unit: equipmentSet.unit,
                target: equipmentSet.quantity,
            })
            this.props.drink.steps.shift();
        }, 1500)
    }

    render() {
        let isLoaded = this.state.isLoaded
        let step = this.state.step;
        let game = null

        console.log(this.props.id, this.props.drinkId, this.props.cohortId)

        if(step && this.state.ingredientPic && this.state.equipmentPic && this.state.target && this.state.unit && this.state.allIngredients) {
            console.log("yes")
            if(step.action == "POURING_LIQUID") {
                game = <PourLiquidGame unit={this.state.unit} next={this.handleNext} target={this.state.target} ingredient_src={this.state.ingredientPic} equipment_src={this.state.equipmentPic} />
            }
            else if(step.action == "POURING_SOLID") {
                game = <PourSolidGame unit={this.state.unit} next={this.handleNext} target={this.state.target} ingredient_src={this.state.ingredientPic} equipment_src={this.state.equipmentPic} />
            }
            else if(step.action == "SHAKE") {
                game = <ShakeGame unit={this.state.unit} next={this.handleNext} target={this.state.target} ingredient_src={this.state.ingredientPic} equipment_src={this.state.equipmentPic} />
            }
            else if(step.action == "PUT") {
                game = <FillGame unit={this.state.unit} next={this.handleNext} target={this.state.target} ingredient_src={this.state.ingredientPic} equipment_src={this.state.equipmentPic} />
            }
            else if(step.action == "ROLL") {
                game = <RollGame unit={this.state.unit} next={this.handleNext} target={this.state.target} ingredient_src={this.state.ingredientPic} equipment_src={this.state.equipmentPic} />
            }
            else if(step.action == "STIR") {
                game = <StirGame unit={this.state.unit} next={this.handleNext} target={this.state.target} ingredient_src={this.state.ingredientPic} equipment_src={this.state.equipmentPic} />
            }
            else if(step.action == "STRAIN") {
                game = <StrainGame unit={this.state.unit} next={this.handleNext} target={this.state.target} ingredient_src={this.state.ingredientPic} equipment_src={this.state.equipmentPic} />
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
