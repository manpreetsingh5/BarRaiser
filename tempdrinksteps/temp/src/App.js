import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import { PourLiquidGame, ShakeGame, PourSolidGame, FillGame, StirGame, MatchingGame, RollGame, StrainGame} from './Games';
import React from 'react';
import './App.css';
import {Container, Tabs, Tab} from 'react-bootstrap';
import bottle_src from './img/vodka.svg';
import glass_src from './img/wine.svg';
import shaker_src from './img/shaker.svg';
import salt_src from './img/salt.svg';
import plate_src from './img/plate.svg';
import ice_src from './img/ice.svg';
import milk_src from './img/milk.svg';
import cookie_src from './img/cookie.svg';
import spoon_src from './img/spoon.svg';

class App extends React.Component {
  render() {
    return (
      <Container>
        {/* Tabs are used just to put examples in one App.js --> real app will not have tabs/tab */}
        <Tabs defaultActiveKey="matching" id="uncontrolled-tab-example">
          <Tab eventKey="matching" title="Matching Game">
            <MatchingGame ingredients={[bottle_src, glass_src, shaker_src, salt_src, plate_src, ice_src, milk_src, spoon_src, cookie_src]} target={bottle_src} length={9} />
          </Tab>
          <Tab eventKey="pourliquid" title="Pour Liquid">
            <PourLiquidGame target={25} unit={"oz"} ingredient_src={bottle_src} equipment_src={glass_src}/>
          </Tab>
          <Tab eventKey="shake" title="Shake">
            <ShakeGame target={10} equipment_src={shaker_src} />
          </Tab>
          <Tab eventKey="strain" title="Strain">
            <StrainGame target={25} unit={"oz"} ingredient_src={shaker_src}  equipment_src={glass_src}/>
          </Tab>
          <Tab eventKey="poursolid" title="Pour Solid">
            <PourSolidGame target={30} unit={"g"} ingredient_src={salt_src}  equipment_src={plate_src}/>
          </Tab>
          <Tab eventKey="fill" title="Fill with Ingredient">
            <FillGame target={5} ingredient_src={ice_src} equipment_src={glass_src}/>
          </Tab>
          <Tab eventKey="stir" title="Stir">
            <StirGame target={40} equipment_src={milk_src}/>
          </Tab>
          <Tab eventKey="roll" title="Roll">
            <RollGame target={33} equipment_src={plate_src}/>
          </Tab>
        </Tabs>
      </Container>
    );
  }
}

export default App;
