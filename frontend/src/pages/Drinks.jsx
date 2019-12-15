import React, { Fragment, Component } from 'react';
import StepCards from './StepCards'
import Empty from './Empty';
import Load from './Load';
import style from '../style/Drinks.module.css';

import Tabs from 'react-bootstrap/Tabs';
import Tab from 'react-bootstrap/Tab';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image';
import Table from 'react-bootstrap/Table';

import { FaPencilAlt } from 'react-icons/fa';
import { FaTrashAlt } from 'react-icons/fa';
import { FaSearch} from 'react-icons/fa';

let step = 0

class Drinks extends Component {
    constructor(props) {
        super(props);

        this.state = {
            show: false,
            showIngredient: false,
            showEquipment: false,
            drinks: [],
            public_drinks: [],
            isLoaded: false,
            steps: [],
            stepObjects: [],
            games: [],
            units: [],
            ingredients: [],
            equipment: [],
            userIngredients: [],
            userEquipment: [],
        };
    }

    componentDidMount() {
        let token = localStorage.getItem("accessToken");
        let id = this.props.id;

        fetch(`api/drink/viewAll`, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json',
            }
        }).then(response => {
            if(response.status !== 200){
                return null;
            }
            console.log(response);
            return response.json().then(
                data => {
                    console.log(data)
                    if(data !== null){
                        this.setState({
                            public_drinks: data
                        })
                        return data;
                    }
                    return null;
                }
            ).then(public_drinks => {
                if(public_drinks !== null && public_drinks.length){
                    let processed = 0;
                    public_drinks.forEach((public_drink) => {
                                public_drink.showView = false;
                                processed++;
                                if (processed === public_drinks.length) {
                                    this.callbackPublicDrinks(public_drinks);
                                }
                            })
                }
            })
        }).then(() => {
            fetch(`api/drink/viewUserDrinks`, {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json',
                }
            })
            .then(response => {
                if (response.status !== 200) {
                    return null;
                }
                return response.json()
                    .then(
                        data => {
                            if (data !== null) {
                                this.setState({
                                    drinks: data
                                })
                                return data;
                            }
                            return null;
                        }
                    )
                    .then(drinks => {
                        if (drinks !== null && drinks.length) {
                            let processed = 0;
                            drinks.forEach((drink) => {
                                drink.showEdit = false
                                drink.showDelete = false;
                                processed++;
                                if (processed === drinks.length) {
                                    this.callbackDrinks(drinks);
                                }
                            })
                        }
                    })
            })
        }).then(() => {
                fetch(`api/equipment/viewAllEquipment`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        equipment: data
                                    })
                                }
                            }
                        )
                    })
            })
            .then(() => {
                fetch(`api/equipment/viewAllIngredients`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        ingredients: data
                                    })
                                }
                            }
                        )
                    })
            })
            .then(() => {
                fetch(`api/actions/getActions`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        games: data,
                                    })
                                }
                            }
                        )
                    })
            })
            .then(() => {
                fetch(`api/equipment/viewUnits`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        units: data,
                                    })
                                }
                            }
                        )
                    })
            })
            .then(() => {
                fetch(`api/equipment/viewUserEquipment`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        userEquipment: data
                                    })
                                    return data
                                }
                                return null
                            }
                        )
                    })
                    .then(equipment => {
                        if (equipment !== null && equipment.length) {
                            let processed = 0;
                            equipment.forEach((equipment) => {
                                equipment.showDelete = false;
                                processed++;
                                if (processed === equipment.length) {
                                    this.callbackEquipment(equipment);
                                }
                            })
                        }
                    })
            })
            .then(() => {
                fetch(`api/equipment/viewUserIngredients`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        userIngredients: data
                                    })
                                    return data
                                }
                                return null
                            }
                        )
                    })
                    .then(ingredients => {
                        if (ingredients !== null && ingredients.length) {
                            let processed = 0;
                            ingredients.forEach((ingredient) => {
                                ingredient.showDelete = false;
                                processed++;
                                if (processed === ingredients.length) {
                                    this.callbackIngredients(ingredients);
                                }
                            })
                        }
                    })
            })
            .then(() => {
                this.setState({
                    isLoaded: true,
                })
            })
    }

    callback = (drinks) => {
        this.setState({
            drinks: drinks,
        })
    }

    callbackPublicDrinks = (public_drinks) => {
        this.setState({
            public_drinks: public_drinks,
        })
    }

    callbackDrinks = (drinks) => {
        this.setState({
            drinks: drinks,
        })
    }

    callbackEquipment = (equipment) => {
        this.setState({
            equipment: equipment,
        })
    }

    callbackIngredients = (ingredients) => {
        this.setState({
            ingredients: ingredients,
        })
    }

    updateView = () => {
        let token = localStorage.getItem("accessToken");
        let id = this.props.id;
        fetch(`api/drink/viewUserDrinks`, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                if (response.status !== 200) {
                    return null;
                }
                return response.json()
                    .then(data => {
                        if (data !== null) {
                            this.setState({
                                drinks: data
                            })
                            return data;
                        }
                        return null;
                    }
                    )
                    .then(drinks => {
                        if (drinks !== null && drinks.length) {
                            let processed = 0;
                            drinks.forEach((drink) => {
                                drink.showEdit = false
                                drink.showDelete = false;
                                processed++;
                                if (processed === drinks.length) {
                                    this.callbackDrinks(drinks);
                                }
                            })
                        }
                    })
            })
            .then(() => {
                fetch(`api/equipment/viewAllEquipment`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        equipment: data
                                    })
                                }
                            }
                        )
                    })
            })
            .then(() => {
                fetch(`api/equipment/viewAllIngredients`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        ingredients: data
                                    })
                                }
                            }
                        )
                    })
            })
            .then(() => {
                fetch(`api/actions/getActions`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        games: data,
                                    })
                                }
                            }
                        )
                    })
            })
            .then(() => {
                fetch(`api/equipment/viewUnits`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        units: data,
                                    })
                                }
                            }
                        )
                    })
            })
            .then(() => {
                fetch(`api/equipment/viewUserEquipment`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        userEquipment: data
                                    })
                                    return data
                                }
                                return null
                            }
                        )
                    })
                    .then(equipment => {
                        if (equipment !== null && equipment.length) {
                            let processed = 0;
                            equipment.forEach((equipment) => {
                                equipment.showDelete = false;
                                processed++;
                                if (processed === equipment.length) {
                                    this.callbackEquipment(equipment);
                                }
                            })
                        }
                    })
            })
            .then(() => {
                fetch(`api/equipment/viewUserIngredients`, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json',
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return null;
                        }
                        return response.json().then(
                            data => {
                                if (data !== null) {
                                    this.setState({
                                        userIngredients: data
                                    })
                                    return data
                                }
                                return null
                            }
                        )
                    })
                    .then(ingredients => {
                        if (ingredients !== null && ingredients.length) {
                            let processed = 0;
                            ingredients.forEach((ingredient) => {
                                ingredient.showDelete = false;
                                processed++;
                                if (processed === ingredients.length) {
                                    this.callbackIngredients(ingredients);
                                }
                            })
                        }
                    })
            })
            .then(() => {
                this.setState({
                    isLoaded: true,
                })
            })
    }

    handleSubmit = (event) => {
        let token = localStorage.getItem("accessToken");
        let form = event.target;
        let file = form.elements.image.files[0];
        let stepcards = this.state.steps;
        let typeval;

        if (form.elements.type[0].checked === true) {
            typeval = true
        }
        else {
            typeval = false
        }

        let steps = []
        stepcards.forEach((el, index) => {
            if (form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id == -1
                && form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id == -1) {
                steps.push({
                    step_number: index,
                    description: form[`stepDescription${index}`].value,
                    action: form[`action${index}`].options[form[`action${index}`].selectedIndex].text,
                    image_path: "",
                    equipmentSet: [],
                })
            }
            else if (form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id == -1
                && form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id != -1) {
                steps.push({
                    step_number: index,
                    description: form[`stepDescription${index}`].value,
                    action: form[`action${index}`].options[form[`action${index}`].selectedIndex].text,
                    image_path: "",
                    equipmentSet: [
                        {
                            equipment: {
                                id: form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id,
                                name: form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].text,
                                image_path: "",
                                type: "INGREDIENT",
                            },
                            quantity: form[`amount${index}`].value,
                            unit: form[`unit${index}`].options[form[`unit${index}`].selectedIndex].text
                        },
                    ],
                })
            }
            else if (form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id != -1
                && form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id == -1) {
                steps.push({
                    step_number: index,
                    description: form[`stepDescription${index}`].value,
                    action: form[`action${index}`].options[form[`action${index}`].selectedIndex].text,
                    image_path: "",
                    equipmentSet: [
                        {
                            equipment: {
                                id: form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id,
                                name: form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].text,
                                image_path: "",
                                type: "EQUIPMENT",
                            },
                            quantity: form[`amount${index}`].value,
                            unit: form[`unit${index}`].options[form[`unit${index}`].selectedIndex].text
                        },
                    ],
                })
            }
            else {
                steps.push({
                    step_number: index,
                    description: form[`stepDescription${index}`].value,
                    action: form[`action${index}`].options[form[`action${index}`].selectedIndex].text,
                    image_path: "",
                    equipmentSet: [
                        {
                            equipment: {
                                id: form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id,
                                name: form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].text,
                                image_path: "",
                                type: "INGREDIENT",
                            },
                            quantity: form[`amount${index}`].value,
                            unit: form[`unit${index}`].options[form[`unit${index}`].selectedIndex].text
                        },
                        {
                            equipment: {
                                id: form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id,
                                name: form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].text,
                                image_path: "",
                                type: "EQUIPMENT",
                            },
                            quantity: form[`amount${index}`].value,
                            unit: form[`unit${index}`].options[form[`unit${index}`].selectedIndex].text
                        }
                    ],
                })
            }
        })

        const drink = {
            name: form.name.value,
            description: form.description.value,
            isPublic: typeval,
            steps: steps,
        }

        const blob = new Blob([JSON.stringify(drink)], {
            type: 'application/json'
        });

        let data = new FormData();
        data.append('file', file);
        data.append('drink', blob);

        fetch('api/drink/addDrink', {
            method: 'POST',
            body: data,
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        })
            .then(response => {
                console.log(response)
            })
            .then(() => {
                this.setState({ isLoaded: false });
                this.handleClose();
                this.updateView();
            })

        step = 0;

        event.preventDefault();
    }

    handleEditDrink = (event) => {
        let token = localStorage.getItem("accessToken");
        let form = event.target;
        let file = form.elements.image.files[0];
        let stepcards = this.state.steps;

        // console.log(form.id.value)

        let steps = []
        stepcards.forEach((el, index) => {
            // console.log(form[`stepId${index}`].value)
            console.log(form[`equipmentSetId${index}`].value)
            if (form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id == -1
                && form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id == -1) {
                steps.push({
                    id: form[`stepId${index}`].value,
                    step_number: index,
                    description: form[`stepDescription${index}`].value,
                    action: form[`action${index}`].options[form[`action${index}`].selectedIndex].text,
                    image_path: "",
                    equipmentSet: [],
                })
            }
            else if (form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id == -1
                && form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id != -1) {
                steps.push({
                    id: form[`stepId${index}`].value,
                    step_number: index,
                    description: form[`stepDescription${index}`].value,
                    action: form[`action${index}`].options[form[`action${index}`].selectedIndex].text,
                    image_path: "",
                    equipmentSet: [
                        {
                            id: form[`ingredientSetId${index}`].value,
                            equipment: {
                                id: form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id,
                                name: form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].text,
                                image_path: "",
                                type: "INGREDIENT",
                            },
                            quantity: form[`amount${index}`].value,
                            unit: form[`unit${index}`].options[form[`unit${index}`].selectedIndex].text
                        },
                    ],
                })
            }
            else if (form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id != -1
                && form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id == -1) {
                steps.push({
                    id: form[`stepId${index}`].value,
                    step_number: index,
                    description: form[`stepDescription${index}`].value,
                    action: form[`action${index}`].options[form[`action${index}`].selectedIndex].text,
                    image_path: "",
                    equipmentSet: [
                        {
                            id: form[`equipmentSetId${index}`].value,
                            equipment: {
                                id: form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id,
                                name: form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].text,
                                image_path: "",
                                type: "EQUIPMENT",
                            },
                            quantity: form[`amount${index}`].value,
                            unit: form[`unit${index}`].options[form[`unit${index}`].selectedIndex].text
                        },
                    ],
                })
            }
            else {
                // console.log(form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id,
                // form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id)
                steps.push({
                    id: form[`stepId${index}`].value,
                    step_number: index,
                    description: form[`stepDescription${index}`].value,
                    action: form[`action${index}`].options[form[`action${index}`].selectedIndex].text,
                    image_path: "",
                    equipmentSet: [
                        {
                            id: form[`ingredientSetId${index}`].value,
                            equipment: {
                                id: form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].id,
                                name: form[`ingredient${index}`].options[form[`ingredient${index}`].selectedIndex].text,
                                image_path: "",
                                type: "INGREDIENT",
                            },
                            quantity: form[`amount${index}`].value,
                            unit: form[`unit${index}`].options[form[`unit${index}`].selectedIndex].text
                        },
                        {
                            id: form[`equipmentSetId${index}`].value,
                            equipment: {
                                id: form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].id,
                                name: form[`equipment${index}`].options[form[`equipment${index}`].selectedIndex].text,
                                image_path: "",
                                type: "EQUIPMENT",
                            },
                            quantity: form[`amount${index}`].value,
                            unit: form[`unit${index}`].options[form[`unit${index}`].selectedIndex].text
                        }
                    ],
                })
            }
        })

        const drink = {
            id: form.id.value,
            name: form.name.value,
            description: form.description.value,
            steps: steps,
        }

        const blob = new Blob([JSON.stringify(drink)], {
            type: 'application/json'
        });

        let data = new FormData();
        data.append('file', file);
        data.append('drink', blob);

        console.log(drink)

        fetch('api/drink/editDrink', {
            method: 'POST',
            body: data,
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        })
            .then(response => {
                console.log(response)
            })
            .then(() => {
                this.setState({ isLoaded: false });
                this.handleEditDrinkClose();
                this.updateView();
            })

        step = 0;

        event.preventDefault();
    }

    handleDeleteDrink = (id) => {
        let token = localStorage.getItem("accessToken");
        fetch(`api/drink/deleteDrink?drinkID=${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        })
            .then(response => {
                console.log(response)
            })
            .then(() => {
                this.setState({ isLoaded: false });
                this.updateView();
                this.handleDeleteDrinkClose(id);
            })
    }

    handleShow = () => { this.setState({ show: true, steps: [] }) }

    handleClose = () => { this.setState({ show: false, steps: [] }) }

    handleEditDrinkShow = (id, drink) => {
        let steps = drink.steps;
        let stepList = [];
        steps.sort((a, b) => (a.step_number > b.step_number) ? 1 : -1);
        // console.log(steps);
        steps.forEach((el, index) => {
            stepList.push(
                <StepCards key={el.step_number} id={el.step_number} ingredients={this.state.ingredients}
                    equipment={this.state.equipment} games={this.state.games}
                    add={this.addToStepObjects} units={this.state.units}
                    action={el.action} description={el.description}
                    equipmentSet={el.equipmentSet} stepId={el.id} />
            )
        })
        this.setState({
            drinks: this.state.drinks.map(el => (el.drink.id === id ? { ...el, showEdit: true } : el)),
            steps: stepList,
        });
    }

    handleViewDrinkClose = (id) => {
        this.setState({
            public_drinks: this.state.public_drinks.map(el => (el.drink.id === id ? { ...el, showView: false } : el))
        });
    }

    handleViewDrinkShow = (id) => {
        this.setState({
            public_drinks: this.state.public_drinks.map(el => (el.drink.id === id ? { ...el, showView: true } : el))
        });
    }

    handleEditDrinkClose = (id) => {
        this.setState({
            drinks: this.state.drinks.map(el => (el.drink.id === id ? { ...el, showEdit: false } : el))
        });
    }

    handleDeleteDrinkShow = (id) => {
        this.setState({
            drinks: this.state.drinks.map(el => (el.drink.id === id ? { ...el, showDelete: true } : el))
        });
    }

    handleDeleteDrinkClose = (id) => {
        this.setState({
            drinks: this.state.drinks.map(el => (el.drink.id === id ? { ...el, showDelete: false } : el))
        });
    }

    handleDeleteIngredientShow = (id) => {
        this.setState({
            userIngredients: this.state.userIngredients.map(el => (el.equipment.id === id ? { ...el, showDelete: true } : el))
        });
    }

    handleDeleteIngredientClose = (id) => {
        this.setState({
            userIngredients: this.state.userIngredients.map(el => (el.equipment.id === id ? { ...el, showDelete: false } : el))
        });
    }

    handleDeleteIngredientShow = (id) => {
        this.setState({
            userIngredients: this.state.userIngredients.map(el => (el.equipment.id === id ? { ...el, showDelete: true } : el))
        });
    }

    handleDeleteIngredientClose = (id) => {
        this.setState({
            userIngredients: this.state.userIngredients.map(el => (el.equipment.id === id ? { ...el, showDelete: false } : el))
        });
    }

    handleDeleteEquipmentShow = (id) => {
        this.setState({
            userEquipment: this.state.userEquipment.map(el => (el.equipment.id === id ? { ...el, showDelete: true } : el))
        });
    }

    handleDeleteEquipmentClose = (id) => {
        this.setState({
            userEquipment: this.state.userEquipment.map(el => (el.equipment.id === id ? { ...el, showDelete: false } : el))
        });
    }

    handleDeleteIngredient = (id) => {
        let token = localStorage.getItem("accessToken");
        fetch(`api/equipment/deleteEquipment?equipment_id=${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        })
            .then(response => { })
            .then(() => {
                this.setState({ isLoaded: false });
                this.updateView();
                this.handleDeleteIngredientClose(id);
            })
    }

    handleDeleteEquipment = (id) => {
        let token = localStorage.getItem("accessToken");
        fetch(`api/equipment/deleteEquipment?equipment_id=${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        })
            .then(response => { })
            .then(() => {
                this.setState({ isLoaded: false });
                this.updateView();
                this.handleDeleteEquipmentClose(id);
            })
    }

    handleSubmitIngredient = (event) => {
        let token = localStorage.getItem("accessToken");
        let form = event.target;
        let file = form.elements.image.files[0];
        let typeval;

        if (form.elements.type[0].checked === true) {
            typeval = true
        }
        else {
            typeval = false
        }

        const ingredient = {
            name: form.elements.name.value,
            description: "",
            isPublic: typeval,
            image_path: "",
            type: "INGREDIENT"
        };

        const blob = new Blob([JSON.stringify(ingredient)], {
            type: 'application/json'
        });

        let data = new FormData();
        data.append('file', file);
        data.append('equipment', blob);

        fetch('api/equipment/addEquipment', {
            method: 'POST',
            body: data,
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        })
            .then(response => { })
            .then(() => {
                this.setState({ isLoaded: false });
                this.updateView();
                this.handleCloseIngredient();
            })

        event.preventDefault();
    }

    handleShowIngredient = () => { this.setState({ showIngredient: true }) }

    handleCloseIngredient = () => { this.setState({ showIngredient: false }) }

    handleSubmitEquipment = (event) => {
        let token = localStorage.getItem("accessToken");
        let form = event.target;
        let file = form.elements.image.files[0];
        let typeval;

        if (form.elements.type[0].checked === true) {
            typeval = true
        }
        else {
            typeval = false
        }

        const ingredient = {
            name: form.elements.name.value,
            description: "",
            isPublic: typeval,
            image_path: "",
            type: "EQUIPMENT"
        };

        const blob = new Blob([JSON.stringify(ingredient)], {
            type: 'application/json'
        });

        let data = new FormData();
        data.append('file', file);
        data.append('equipment', blob);

        fetch('api/equipment/addEquipment', {
            method: 'POST',
            body: data,
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        })
            .then(response => { })
            .then(() => {
                this.setState({ isLoaded: false });
                this.updateView();
                this.handleCloseEquipment();
            })

        event.preventDefault();
    }

    handleShowEquipment = () => { this.setState({ showEquipment: true }) }

    handleCloseEquipment = () => { this.setState({ showEquipment: false }) }

    addStep = () => {
        this.setState({
            steps: [
                ...this.state.steps,
                <StepCards key={step} id={step++} ingredients={this.state.ingredients} equipment={this.state.equipment} games={this.state.games} add={this.addToStepObjects} units={this.state.units} />
            ]
        });
    }

    deleteStep = () => {
        if (this.state.steps.length > 0) {
            let arr = [...this.state.steps];
            arr.pop();
            step--;
            this.setState({
                steps: arr,
            });
        }
    }

    addToStepObjects = (obj) => {
        console.log("lol")
    }

    showDrinkSteps = (steps) => {
        let steps_display = [];

        steps.sort((a, b) => (a.step_number > b.step_number) ? 1 : -1);

        steps.forEach(element => steps_display.push(<li>{element.description}</li>) );
        return steps_display;
    }

    handleChange = () => { }

    render() {
        let show = this.state.show;
        let showIngredient = this.state.showIngredient;
        let showEquipment = this.state.showEquipment;
        let empty = null;
        let public_empty = null;
        let ingredientsEmpty = null;
        let equipmentEmpty = null;
        let isLoaded = this.state.isLoaded;
        let drinksList = []
        let publicDrinksList = []
        let ingredientsList = []
        let equipmentList = []
        let drinks = this.state.drinks;
        let public_drinks = this.state.public_drinks;
        let userIngredients = this.state.userIngredients;
        let userEquipment = this.state.userEquipment;
        let games = this.state.games

        if (public_drinks.length){
            public_drinks.sort((a, b) => (a.drink.name > b.drink.name) ? 1 : -1);
            public_drinks.forEach(el =>{
                console.log(el);
                publicDrinksList.push(
                    <Col key={el.drink.id} sm={3}>
                        <Card className={style.card}>
                            <Image src={`data:image/png;base64,${el.file}`} fluid />
                            <div className={style.cardContentDiv}>
                                <h5>{el.drink.name[0].toUpperCase() + el.drink.name.slice(1)}</h5>
                            </div>

                            <Button variant="dark" onClick={() => this.handleViewDrinkShow(el.drink.id, el.drink)} className={style.editDrink}>
                                <div className={style.barButtonsDiv}>
                                    <FaSearch />
                                    <span className={style.buttonText}>View</span>
                                </div>
                            </Button>

                            <Modal show={el.showView} onHide={() => this.handleViewDrinkClose(el.drink.id)} centered>
                                <Modal.Body>
                                    <p><strong>Name: </strong>{el.drink.name}</p>
                                    <p><strong>Author: </strong>{el.drink.createdBy}</p>
                                    <p><strong>Description: </strong>{el.drink.description}</p>
                                    <p><strong>Steps:</strong></p>
                                    <ol>
                                        {this.showDrinkSteps(el.drink.steps)}
                                    </ol>
                                </Modal.Body>
                            </Modal>

                        </Card>
                    </Col>
                )}
            )
        }
        // make changes here - so its view modal -- looking up steps

        if (drinks.length) {
            drinks.sort((a, b) => (a.drink.name > b.drink.name) ? 1 : -1);
            drinks.forEach(el =>
                drinksList.push(
                    <Col key={el.drink.id} sm={3}>
                        <Card className={style.card}>
                            <Image src={`data:image/png;base64,${el.file}`} fluid />
                            <div className={style.cardContentDiv}>
                                <h5>{el.drink.name[0].toUpperCase() + el.drink.name.slice(1)}</h5>
                            </div>

                            <Button variant="dark" onClick={() => this.handleEditDrinkShow(el.drink.id, el.drink)} className={style.editDrink}>
                                <div className={style.barButtonsDiv}>
                                    <FaPencilAlt />
                                    <span className={style.buttonText}>Edit</span>
                                </div>
                            </Button>

                            <Modal show={el.showEdit} onHide={() => this.handleEditDrinkClose(el.drink.id)} centered>
                                <Modal.Header>
                                    <Modal.Title>Edit Drink</Modal.Title>
                                </Modal.Header>

                                <Modal.Body>
                                    <Form onSubmit={this.handleEditDrink}>
                                        <Form.Group controlId="id" className={style.hide}>
                                            <Form.Control
                                                defaultValue={el.drink.id}
                                            />
                                        </Form.Group>

                                        <Form.Group controlId="name">
                                            <Form.Label>Name</Form.Label>

                                            <Form.Control
                                                required
                                                type="name"
                                                defaultValue={el.drink.name}
                                                placeholder="Enter name"
                                            />

                                        </Form.Group>

                                        <Form.Group controlId="description">
                                            <Form.Label>Description</Form.Label>
                                            <Form.Control
                                                required
                                                as="textarea"
                                                defaultValue={el.drink.description}
                                                placeholder="Enter description"
                                            />
                                        </Form.Group>

                                        <Form.Group controlId="image">
                                            <Form.Label>Image</Form.Label>
                                            <Form.Control
                                                required
                                                type="file"
                                            />
                                        </Form.Group>

                                        {this.state.steps}

                                        <Button onClick={this.addStep}>
                                            Add Step
                                        </Button>

                                        <Button onClick={this.deleteStep}>
                                            Delete Step
                                        </Button>

                                        <Modal.Footer>
                                            <Button variant="secondary" onClick={() => this.handleEditDrinkClose(el.drink.id)}>
                                                Close
                                            </Button>

                                            <Button variant="primary" type="submit">
                                                Edit Drink
                                            </Button>
                                        </Modal.Footer>
                                    </Form>
                                </Modal.Body>
                            </Modal>

                            <Button variant="dark" onClick={() => this.handleDeleteDrinkShow(el.drink.id)}>
                                <div className={style.barButtonsDiv}>
                                    <FaTrashAlt />
                                    <span className={style.buttonText}>Delete</span>
                                </div>
                            </Button>

                            <Modal show={el.showDelete} onHide={() => this.handleDeleteDrinkClose(el.drink.id)} centered dialogClassName={style.deleteModal}>
                                <Modal.Header className={style.deleteHeader}>
                                    <Modal.Title>Are you sure you want to delete {el.drink.name}?</Modal.Title>
                                </Modal.Header>

                                <Modal.Body className={style.deleteModalButtons}>
                                    <Button variant="danger" className={style.deleteButton} onClick={() => this.handleDeleteDrink(el.drink.id)}>
                                        Delete
                                    </Button>
                                    <Button variant="primary" onClick={() => this.handleDeleteDrinkClose(el.drink.id)} className={style.returnButton}>
                                        Return
                                    </Button>
                                </Modal.Body>
                            </Modal>
                        </Card>
                    </Col>
                )
            )
        }

        if (userIngredients.length) {
            userIngredients.sort((a, b) => (a.equipment.name > b.equipment.name) ? 1 : -1)
            userIngredients.forEach(el =>
                ingredientsList.push(
                    <Col key={el.equipment.id} sm={2}>
                        <Card className={style.card}>
                            <Image src={`data:image/png;base64,${el.file}`} fluid />
                            <div className={style.cardContentDiv}>
                                <h5>{el.equipment.name[0].toUpperCase() + el.equipment.name.slice(1)}</h5>
                            </div>
                            <Button variant="dark" onClick={() => this.handleDeleteIngredientShow(el.equipment.id)}>
                                <div className={style.barButtonsDiv}>
                                    <FaTrashAlt />
                                    <span className={style.buttonText}>Delete</span>
                                </div>
                            </Button>

                            <Modal show={el.showDelete} onHide={() => this.handleDeleteIngredientClose(el.equipment.id)} centered dialogClassName={style.deleteModal}>
                                <Modal.Header className={style.deleteHeader}>
                                    <Modal.Title>Are you sure you want to delete {el.equipment.name}?</Modal.Title>
                                </Modal.Header>

                                <Modal.Body className={style.deleteModalButtons}>
                                    <Button variant="danger" className={style.deleteButton} onClick={() => this.handleDeleteIngredient(el.equipment.id)}>
                                        Delete
                                    </Button>
                                    <Button variant="primary" onClick={() => this.handleDeleteIngredientClose(el.equipment.id)} className={style.returnButton}>
                                        Return
                                    </Button>
                                </Modal.Body>
                            </Modal>
                        </Card>
                    </Col>
                )
            )
        }

        if (userEquipment.length) {
            userEquipment.sort((a, b) => (a.equipment.name > b.equipment.name) ? 1 : -1)
            userEquipment.forEach(el =>
                equipmentList.push(
                    <Col key={el.equipment.id} sm={2}>
                        <Card className={style.card}>
                            <Image src={`data:image/png;base64,${el.file}`} fluid />
                            <div className={style.cardContentDiv}>
                                <h5>{el.equipment.name[0].toUpperCase() + el.equipment.name.slice(1)}</h5>
                            </div>
                            <Button variant="dark" onClick={() => this.handleDeleteEquipmentShow(el.equipment.id)}>
                                <div className={style.barButtonsDiv}>
                                    <FaTrashAlt />
                                    <span className={style.buttonText}>Delete</span>
                                </div>
                            </Button>

                            <Modal show={el.showDelete} onHide={() => this.handleDeleteEquipmentClose(el.equipment.id)} centered dialogClassName={style.deleteModal}>
                                <Modal.Header className={style.deleteHeader}>
                                    <Modal.Title>Are you sure you want to delete {el.equipment.name}?</Modal.Title>
                                </Modal.Header>

                                <Modal.Body className={style.deleteModalButtons}>
                                    <Button variant="danger" className={style.deleteButton} onClick={() => this.handleDeleteEquipment(el.equipment.id)}>
                                        Delete
                                    </Button>
                                    <Button variant="primary" onClick={() => this.handleDeleteEquipmentClose(el.equipment.id)} className={style.returnButton}>
                                        Return
                                    </Button>
                                </Modal.Body>
                            </Modal>
                        </Card>
                    </Col>
                )
            )
        }

        if (!publicDrinksList.length) {
            public_empty = (
                <div className={style.emptyDiv}>
                    <Empty />
                    <h5>No Data</h5>
                </div>
            )
        }

        if (!drinksList.length) {
            empty = (
                <div className={style.emptyDiv}>
                    <Empty />
                    <h5>No Data</h5>
                </div>
            )
        }

        if (!ingredientsList.length) {
            ingredientsEmpty = (
                <div className={style.emptyDiv}>
                    <Empty />
                    <h5>No Data</h5>
                </div>
            )
        }

        if (!equipmentList.length) {
            equipmentEmpty = (
                <div className={style.emptyDiv}>
                    <Empty />
                    <h5>No Data</h5>
                </div>
            )
        }

        if (!isLoaded) {
            return (
                <Fragment>
                    <Row className={style.titleContainer}>
                        <div className={style.titleDiv}>
                            <h3>Drinks</h3>
                        </div>
                    </Row>
                    <Load />
                </Fragment>
            )
        }
        else {
            return (
                <Fragment>
                    <Row className={style.titleContainer}>
                        <div className={style.titleDiv}>
                            <h3>Drinks</h3>
                        </div>
                    </Row>

                    <Container className="my-4">
                        <Tabs defaultActiveKey="public_drinks" className={style.navTabs} id="drinks-tabs">
                            <Tab eventKey="public_drinks" title="Public Drinks">
                                <Container>
                                    <Row className={style.contentContainer}>
                                        <div className={style.contentDiv}>
                                            {public_empty}
                                            <Row> 
                                                {publicDrinksList}
                                            </Row>
                                        </div>
                                    </Row>
                                </Container>
                            </Tab>
                            <Tab eventKey="your_drinks" title="Your Drinks">
                                <Container>
                                    <Row className={style.contentContainer}>
                                        <div className={style.contentDiv}>
                                            <div className="text-center">
                                                <Button variant="primary" onClick={this.handleShow}>
                                                    Add Drinks
                                            </Button>
                                                <Modal show={show} onHide={this.handleClose} centered dialogClassName={style.modal}>
                                                    <Modal.Header>
                                                        <Modal.Title>Add Drink</Modal.Title>
                                                    </Modal.Header>

                                                    <Modal.Body>
                                                        <Form onSubmit={this.handleSubmit}>
                                                            <Form.Group controlId="name">
                                                                <Form.Label>Name</Form.Label>

                                                                <Form.Control
                                                                    required
                                                                    type="name"
                                                                    placeholder="Enter name"
                                                                />

                                                            </Form.Group>

                                                            <Form.Group controlId="description">
                                                                <Form.Label>Description</Form.Label>
                                                                <Form.Control
                                                                    required
                                                                    as="textarea"
                                                                    placeholder="Enter description"
                                                                />
                                                            </Form.Group>

                                                            <Form.Group controlId="image">
                                                                <Form.Label>Image</Form.Label>
                                                                <Form.Control
                                                                    required
                                                                    type="file"
                                                                />
                                                            </Form.Group>

                                                            <Form.Group controlId="type">
                                                                <Form.Check id="true" type="radio" onChange={this.handleChange} name="type" label="Public" inline checked />
                                                                <Form.Check id="false" type="radio" name="type" onChange={this.handleChange} label="Private" inline />
                                                            </Form.Group>

                                                            {this.state.steps}

                                                            <Button className="mr-3" onClick={this.addStep}>
                                                                Add Step
                                                            </Button>

                                                            <Button onClick={this.deleteStep}>
                                                                Delete Step
                                                </Button>

                                                            <Modal.Footer>
                                                                <Button variant="secondary" onClick={this.handleClose}>
                                                                    Close
                                                    </Button>

                                                                <Button variant="primary" type="submit">
                                                                    Add Drink
                                                    </Button>
                                                            </Modal.Footer>
                                                        </Form>
                                                    </Modal.Body>
                                                </Modal>
                                            </div>
                                            {empty}
                                            <div className={style.drinksList}>
                                                {drinksList}
                                            </div>
                                        </div>
                                    </Row>
                                </Container>
                            </Tab>


                            <Tab eventKey="ingredients" title="Your Ingredients">
                                <Container>
                                    <Row className={style.contentContainer}>
                                        <div className={style.contentDiv}>
                                            <div className="text-center">
                                                <Button variant="primary" onClick={this.handleShowIngredient}>
                                                    Add Ingredient
                                                </Button>

                                                <Modal show={showIngredient} onHide={this.handleCloseIngredient} centered>
                                                    <Modal.Header>
                                                        <Modal.Title>Add Ingredient</Modal.Title>
                                                    </Modal.Header>

                                                    <Modal.Body>
                                                        <Form onSubmit={this.handleSubmitIngredient}>
                                                            <Form.Group controlId="name">
                                                                <Form.Label>Name</Form.Label>

                                                                <Form.Control
                                                                    required
                                                                    type="name"
                                                                    placeholder="Enter name"
                                                                />

                                                            </Form.Group>

                                                            <Form.Group controlId="image">
                                                                <Form.Label>Image</Form.Label>
                                                                <Form.Control
                                                                    required
                                                                    type="file"
                                                                />
                                                            </Form.Group>

                                                            <Form.Group controlId="type">
                                                                <Form.Check id="true" type="radio" onChange={this.handleChange} name="type" label="Public" inline checked />
                                                                <Form.Check id="false" type="radio" name="type" onChange={this.handleChange} label="Private" inline />
                                                            </Form.Group>

                                                            <Modal.Footer>
                                                                <Button variant="secondary" onClick={this.handleCloseIngredient}>
                                                                    Close
                                                    </Button>

                                                                <Button variant="primary" type="submit">
                                                                    Add Ingredient
                                                    </Button>
                                                            </Modal.Footer>
                                                        </Form>
                                                    </Modal.Body>
                                                </Modal>
                                            </div>
                                            {ingredientsEmpty}
                                            <Row className={style.ingredientsList}>
                                                {ingredientsList}
                                            </Row>

                                        </div>
                                    </Row>
                                </Container>
                            </Tab>

                            <Tab eventKey="equipment" title="Your Equipment">
                                <Container>
                                    <Row className={style.contentContainer}>
                                        <div className={style.contentDiv}>
                                            <div className="text-center">
                                                <Button variant="primary" onClick={this.handleShowEquipment}>
                                                    Add Equipment
                                                </Button>

                                                <Modal show={showEquipment} onHide={this.handleCloseEquipment} centered>
                                                    <Modal.Header>
                                                        <Modal.Title>Add Equipment</Modal.Title>
                                                    </Modal.Header>

                                                    <Modal.Body>
                                                        <Form onSubmit={this.handleSubmitEquipment}>
                                                            <Form.Group controlId="name">
                                                                <Form.Label>Name</Form.Label>

                                                                <Form.Control
                                                                    required
                                                                    type="name"
                                                                    placeholder="Enter name"
                                                                />

                                                            </Form.Group>

                                                            <Form.Group controlId="image">
                                                                <Form.Label>Image</Form.Label>
                                                                <Form.Control
                                                                    required
                                                                    type="file"
                                                                />
                                                            </Form.Group>

                                                            <Form.Group controlId="type">
                                                                <Form.Check id="true" type="radio" onChange={this.handleChange} name="type" label="Public" inline checked />
                                                                <Form.Check id="false" type="radio" name="type" onChange={this.handleChange} label="Private" inline />
                                                            </Form.Group>

                                                            <Modal.Footer>
                                                                <Button variant="secondary" onClick={this.handleCloseEquipment}>
                                                                    Close
                                                    </Button>

                                                                <Button variant="primary" type="submit">
                                                                    Add Equipment
                                                    </Button>
                                                            </Modal.Footer>
                                                        </Form>
                                                    </Modal.Body>
                                                </Modal>
                                            </div>
                                            {equipmentEmpty}
                                            <Row className={style.equipmentList}>
                                                {equipmentList}
                                            </Row>
                                        </div>
                                    </Row>
                                </Container>
                            </Tab>

                        </Tabs>
                    </Container>

                </Fragment>
            );
        }
    }
}

export default Drinks;
