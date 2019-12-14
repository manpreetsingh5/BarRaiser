import Repeatable from 'react-repeatable';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import React from 'react';
import styles from '../style/Games.module.css';
import { Button, Container, Row, ProgressBar, Col, Modal } from 'react-bootstrap';
import posed from 'react-pose';
import spoon_src from '../img/spoon.png';
import strainer_src from '../img/strainer.png';
import circle_src from '../img/circle.png';
import blender_src from '../img/blender.png';

const Bottle = posed.img({
  standing: { rotate: '0deg' },
  pouring: {
    rotate: '-165deg',
    transition: { duration: 400 }
  }
});

const PourLiquid = posed.div({
  standing: { height: '0' },
  pouring: {
    height: '250px', 
    delay: 200,
  }
});

const StrainLiquid = posed.div({
  standing: { height: '0' },
  pouring: {
    height: '400px',
    delay: 200,
  }
})

const PourSolid = posed.div({
  standing: { height: '0px' },
  pouring: {
    height: '200px',
    delay: 200,
  }
});

const Shaker = posed.img({
  up: { y: 0 },
  down: { y: 100 }
});

const Ingredient = posed.img({
  up: { y: 0 },
  down: {
    y: 100,
    transition: { duration: 50 },
  }
});

const FilledIngredient = posed.img({
  visible: { opacity: 1 },
  hidden: { opacity: 0 },
})

const Spoon = posed.img({
  left: { x: -30 },
  right: { x: 30 }
})

const Blender = posed.img({
  one: {
    x: 0,
    y: 0,
    rotate: '0deg',
  },
  two: {
    x: -30,
    y: -20,
    rotate: '-10deg',
  },
  three: {
    x: 30,
    y: -20,
    rotate: '10deg',
  },
})

const PosedH5 = posed.h5({
  visible: { opacity: 1 },
  hidden: { opacity: 0 },
})

const PosedProgressBar = posed(React.forwardRef((props, ref) => <ProgressBar {...props} ref={ref} />))({
  visible: { opacity: 1 },
  hidden: { opacity: 0 },
});

export class PourLiquidGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    }
  }

  handleCloseModal() {
    this.setState({
      show_modal: false,
    })
  }

  handleOpenModal() {
    var result = (this.state.progress === this.state.target);
    this.setState({
      completed: true,
      show_modal: true,
      success: result,
    })
  }

  getResult() {
    var result = (this.state.success);
    if (result) {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Congratulations! You've successfully completed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.setup}>
              Continue
            </Button>
          </Modal.Footer>
        </div>
      );
    } else {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Failed</Modal.Title>
          </Modal.Header>
          <Modal.Body>Unfortunately, you've failed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.refresh}>
              Try Again
            </Button>
          </Modal.Footer>
        </div>
      );
    }
  }

  setup = () => {
    this.refresh();
    this.props.next();
    }

  refresh = () => {
    this.setState({
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    });
    }

  render() {
    //   console.log(this.props.ingredient_src)
    return (
      <Container>
        <Row className="mt-5">
          <Col>
            <ProgressBar>
              <ProgressBar animated variant="info" now={(this.state.progress * 10)} key={1} />
              <PosedProgressBar animated variant="danger" now={(this.state.target * 10) - (this.state.progress * 10)} key={2} pose={this.state.hint ? 'visible' : 'hidden'} />
            </ProgressBar>
          </Col>
        </Row>
        <Container>
          <Row className="mt-5">
            <Col sm={8}>
              <Row>
                <Col sm={3} className="mx-auto">
                  <Bottle className={`${styles.front} img-fluid test`} id="pour_liquid_top" src={`data:image/png;base64,${this.props.ingredient_src}`} alt={'ingredient'} pose={this.state.pressed ? 'pouring' : 'standing'} />
                </Col>
              </Row>
              <Row className={styles.pourRow}>
                <Col sm={3} className="mx-auto">
                  <PourLiquid className={`mx-auto ${styles.pour_liquid}`} pose={this.state.pressed ? 'pouring' : 'standing'} />
                </Col>
              </Row>
              <Row>
                <Col sm={3} className="mx-auto">
                  <img className="img-fluid" id="pour_liquid_bottom" src={`data:image/png;base64,${this.props.equipment_src}`} alt={'equipment'} />
                </Col>
              </Row>
            </Col>
            <Col sm={2} className="mx-auto">
              <PosedH5 pose={this.state.hint ? 'visible' : 'hidden'}>Target: {this.state.target + " " + this.props.unit}</PosedH5>
              <h5>Current: {this.state.progress + " " + this.props.unit}</h5>
              <Row className="my-3 mt-5">
                <Repeatable
                  tag={Button}
                  variant="info"
                  disabled={this.state.completed}
                  repeatDelay={0}
                  repeatInterval={150}
                  onPress={() => {
                    this.setState({
                      pressed: true,
                    })
                  }}
                  onHoldStart={() => {
                  }}
                  onHold={() => {
                    this.setState({
                      progress: Math.min((this.state.progress + 0.1).toFixed(2), 100)
                    });
                  }}
                  onHoldEnd={() => {
                  }}
                  onRelease={() => {
                    this.setState({
                      pressed: false,
                    })
                  }}
                >
                  POUR
                </Repeatable>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => this.handleOpenModal()}
                >COMPLETE</Button>
                <Modal show={this.state.show_modal} onHide={() => this.handleCloseModal()}>
                  <Modal.Body>{this.getResult()}</Modal.Body>
                </Modal>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => {
                    this.setState({
                      hint: true,
                    });
                  }}
                  disabled={this.state.hint || this.state.completed}
                >GET HINT</Button>
              </Row>
            </Col>
          </Row>
        </Container>
      </Container>
    );
  }
}

export class StrainGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    }
  }

  handleCloseModal() {
    this.setState({
      show_modal: false,
    })
  }

  handleOpenModal() {
    var result = (this.state.progress === this.state.target);
    this.setState({
      completed: true,
      show_modal: true,
      success: result,
    })
  }

  getResult() {
    var result = (this.state.success);
    if (result) {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Congratulations! You've successfully completed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.setup}>
              Continue
            </Button>
          </Modal.Footer>
        </div>
      );
    } else {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Failed</Modal.Title>
          </Modal.Header>
          <Modal.Body>Unfortunately, you've failed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.refresh}>
              Try Again
            </Button>
          </Modal.Footer>
        </div>
      );
    }
  }

  setup = () => {
      this.refresh();
      this.props.next();
  }

  refresh = () => {
    this.setState({
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    });
    }

  render() {
    return (
      <Container>
        <Row className="mt-5">
          <Col>
            <ProgressBar>
              <ProgressBar animated variant="info" now={(this.state.progress * 10)} key={1} />
              <PosedProgressBar animated variant="danger" now={(this.state.target * 10) - (this.state.progress * 10)} key={2} pose={this.state.hint ? 'visible' : 'hidden'} />
            </ProgressBar>
          </Col>
        </Row>
        <Container>
          <Row className="mt-5">
            <Col sm={8}>
              <Row>
                <Col sm={3} className="mx-auto">
                  <Bottle className={`${styles.front} img-fluid test`} src={`data:image/png;base64,${this.props.ingredient_src}`} alt={'ingredient'} pose={this.state.pressed ? 'pouring' : 'standing'} />
                </Col>
              </Row>
              <Row className={styles.pourRow}>
                <Col sm={3} className="mx-auto">
                  <StrainLiquid className={`mx-auto ${styles.pour_liquid}`} pose={this.state.pressed ? 'pouring' : 'standing'} />
                </Col>
              </Row>
              <Row>
                <Col sm={3} className="mx-auto">
                  <img className="img-fluid" src={strainer_src} alt={'strainer'} />
                </Col>
              </Row>
              <Row>
                <Col sm={3} className="mx-auto">
                  <img className="img-fluid" src={`data:image/png;base64,${this.props.equipment_src}`} alt={'equipment'} />
                </Col>
              </Row>
            </Col>
            <Col sm={2} className="mx-auto">
              <PosedH5 pose={this.state.hint ? 'visible' : 'hidden'}>Target: {this.state.target + " " + this.props.unit}</PosedH5>
              <h5>Current: {this.state.progress + " " + this.props.unit}</h5>
              <Row className="my-3 mt-5">
                <Repeatable
                  tag={Button}
                  variant="info"
                  disabled={this.state.completed}
                  repeatDelay={0}
                  repeatInterval={150}
                  onPress={() => {
                    this.setState({
                      pressed: true,
                    })
                  }}
                  onHoldStart={() => {
                  }}
                  onHold={() => {
                    this.setState({
                      progress: Math.min((this.state.progress + 0.1).toFixed(2), 100)
                    });
                  }}
                  onHoldEnd={() => {
                  }}
                  onRelease={() => {
                    this.setState({
                      pressed: false,
                    })
                  }}
                >
                  STRAIN
                </Repeatable>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => this.handleOpenModal()}
                >COMPLETE</Button>
                <Modal show={this.state.show_modal} onHide={() => this.handleCloseModal()}>
                  <Modal.Body>{this.getResult()}</Modal.Body>
                </Modal>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => {
                    this.setState({
                      hint: true,
                    });
                  }}
                  disabled={this.state.hint || this.state.completed}
                >GET HINT</Button>
              </Row>
            </Col>
          </Row>
        </Container>
      </Container>
    );
  }
}

export class PourSolidGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    }
  }

  handleCloseModal() {
    this.setState({
      show_modal: false,
    })
  }

  handleOpenModal() {
    var result = (this.state.progress === this.state.target);
    this.setState({
      completed: true,
      show_modal: true,
      success: result,
    })
  }

  getResult() {
    var result = (this.state.success);
    if (result) {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Congratulations! You've successfully completed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.setup}>
              Continue
            </Button>
          </Modal.Footer>
        </div>
      );
    } else {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Failed</Modal.Title>
          </Modal.Header>
          <Modal.Body>Unfortunately, you've failed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.refresh}>
              Try Again
            </Button>
          </Modal.Footer>
        </div>
      );
    }
  }

  checkProgress() {
    if (!this.state.completed && this.state.progress > (this.state.target)) {
      this.setState({
        completed: true,
        drink_result: 'FAILED',
        progress: 0,
      })
    }
  }

  setup = () => {
    this.refresh();
    this.props.next();
    }

  refresh = () => {
    this.setState({
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    });
    }

  render() {
    return (
      <Container>
        <Row className="mt-5">
          <Col>
            <ProgressBar>
              <ProgressBar animated variant="info" now={(this.state.progress * 10)} key={1} />
              <PosedProgressBar animated variant="danger" now={(this.state.target * 10) - (this.state.progress * 10)} key={2} pose={this.state.hint ? 'visible' : 'hidden'} />
            </ProgressBar>
          </Col>
        </Row>
        <Container>
          <Row className="mt-5">
            <Col sm={8}>
              <Row>
                <Col sm={3} className="mx-auto">
                  <Bottle className={`${styles.front} img-fluid test`} src={`data:image/png;base64,${this.props.ingredient_src}`} alt={'ingredient'} pose={this.state.pressed ? 'pouring' : 'standing'} />
                </Col>
              </Row>
              <Row className={styles.pourRow}>
                <Col sm={3} className="mx-auto">
                  <PourSolid className={`mx-auto ${styles.pour_solid}`} pose={this.state.pressed ? 'pouring' : 'standing'} />
                </Col>
              </Row>
              <Row>
                <Col sm={3} className="mx-auto">
                  <img className="img-fluid" src={`data:image/png;base64,${this.props.equipment_src}`} alt={'equipment'} />
                </Col>
              </Row>
            </Col>
            <Col sm={2} className="mx-auto">
              <PosedH5 pose={this.state.hint ? 'visible' : 'hidden'}>Target: {this.state.target + " " + this.props.unit}</PosedH5>
              <h5>Current: {this.state.progress + " " + this.props.unit}</h5>
              <Row className="my-3 mt-5">
                <Repeatable
                  tag={Button}
                  variant="info"
                  disabled={this.state.completed}
                  repeatDelay={0}
                  repeatInterval={150}
                  onPress={() => {
                    this.setState({
                      pressed: true,
                    })
                  }}
                  onHoldStart={() => {
                  }}
                  onHold={() => {
                    this.setState({
                      progress: Math.min((this.state.progress + 0.1).toFixed(2), 100)
                    });
                  }}
                  onHoldEnd={() => {
                  }}
                  onRelease={() => {
                    this.setState({
                      pressed: false,
                    })
                  }}
                >
                  POUR
                </Repeatable>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => this.handleOpenModal()}
                >COMPLETE</Button>
                <Modal show={this.state.show_modal} onHide={() => this.handleCloseModal()}>
                  <Modal.Body>{this.getResult()}</Modal.Body>
                </Modal>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => {
                    this.setState({
                      hint: true,
                    });
                  }}
                  disabled={this.state.hint || this.state.completed}
                >GET HINT</Button>
              </Row>
            </Col>
          </Row>
        </Container>
      </Container>
    );
  }
}

// for the shake game -- the limit is 10 
export class ShakeGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: (this.props.target * 10),
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    }
  }

  handleCloseModal() {
    this.setState({
      show_modal: false,
    })
  }

  handleOpenModal() {
    var result = (this.state.progress === this.state.target);
    this.setState({
      completed: true,
      show_modal: true,
      success: result,
    })
  }

  getResult() {
    var result = (this.state.success);
    if (result) {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Congratulations! You've successfully completed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.setup}>
              Continue
            </Button>
          </Modal.Footer>
        </div>
      );
    } else {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Failed</Modal.Title>
          </Modal.Header>
          <Modal.Body>Unfortunately, you've failed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.refresh}>
              Try Again
            </Button>
          </Modal.Footer>
        </div>
      );
    }
  }

  setup = () => {
    this.refresh();
    this.props.next();
    }

  refresh = () => {
    this.setState({
      progress: 0,
      target: (this.props.target * 10),
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    });
    }

  render() {
    return (
      <Container>
        <Row className="mt-5">
          <Col>
            <ProgressBar>
              <ProgressBar animated variant="info" now={this.state.progress} key={1} />
              <PosedProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} pose={this.state.hint ? 'visible' : 'hidden'} />
            </ProgressBar>
          </Col>
        </Row>
        <Container>
          <Row className="mt-5">
            <Col sm={8}>
              <Row>
                <Col sm={3} className="mx-auto">
                  <Shaker className="img-fluid" src={`data:image/png;base64,${this.props.equipment_src}`} alt={'equipment'} pose={this.state.pressed ? 'up' : 'down'} />
                </Col>
              </Row>
            </Col>
            <Col sm={2} className="mx-auto">
              <PosedH5 pose={this.state.hint ? 'visible' : 'hidden'}>Target: {(this.state.target / 10)+ " " + this.props.unit}</PosedH5>
              <h5>Current: {(this.state.progress / 10) + " " + this.props.unit}</h5>
              <Row className="my-3 mt-5">
                <Repeatable
                  tag={Button}
                  variant="info"
                  disabled={this.state.completed}
                  repeatDelay={0}
                  repeatInterval={150}
                  onPress={() => {
                    this.setState({
                      pressed: true,
                      progress: Math.min(this.state.progress + 10, 100),
                    })
                  }}
                  onHoldStart={() => {
                  }}
                  onHold={() => {
                  }}
                  onHoldEnd={() => {
                  }}
                  onRelease={() => {
                    this.setState({
                      pressed: false,
                    })
                  }}
                >
                  SHAKE
                </Repeatable>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => this.handleOpenModal()}
                >COMPLETE</Button>
                <Modal show={this.state.show_modal} onHide={() => this.handleCloseModal()}>
                  <Modal.Body>{this.getResult()}</Modal.Body>
                </Modal>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => {
                    this.setState({
                      hint: true,
                    });
                  }}
                  disabled={this.state.hint || this.state.completed}
                >GET HINT</Button>
              </Row>
            </Col>
          </Row>
        </Container>
      </Container>
    );
  }
}


// for the fill game -- the limit is 10 
export class FillGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: (this.props.target * 10),
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    }
  }

  handleCloseModal() {
    this.setState({
      show_modal: false,
    })
  }

  handleOpenModal() {
    var result = (this.state.progress === this.state.target);
    this.setState({
      completed: true,
      show_modal: true,
      success: result,
    })
  }

  getResult() {
    var result = (this.state.success);
    if (result) {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Congratulations! You've successfully completed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.setup}>
              Continue
            </Button>
          </Modal.Footer>
        </div>
      );
    } else {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Failed</Modal.Title>
          </Modal.Header>
          <Modal.Body>Unfortunately, you've failed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.refresh}>
              Try Again
            </Button>
          </Modal.Footer>
        </div>
      );
    }
  }

  setup = () => {
    this.refresh();
    this.props.next();
    }

    refresh = () => {
        this.setState({
        progress: 0,
        target: (this.props.target * 10),
        pressed: false,
        completed: false,
        success: false,
        hint: false,
        show_modal: false,
        });
    }

  render() {
    return (
      <Container>
        <Row className="mt-5">
          <Col>
            <ProgressBar>
              <ProgressBar animated variant="info" now={this.state.progress} key={1} />
              <PosedProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} pose={this.state.hint ? 'visible' : 'hidden'} />
            </ProgressBar>
          </Col>
        </Row>
        <Container>
          <Row className="mt-5">
            <Col sm={8}>
              <Row>
                <Col sm={2} className="mx-auto">
                  <Ingredient className={`${styles.front} img-fluid test`} src={`data:image/png;base64,${this.props.ingredient_src}`} alt={'ingredient'} pose={this.state.pressed ? 'down' : 'up'} />
                </Col>
              </Row>
              <Row>
                <Col sm={6} className="mx-auto">
                  <Row>
                    <Col sm={10} className="mx-auto">
                      <img className="img-fluid" src={`data:image/png;base64,${this.props.equipment_src}`} alt={'equipment'} />
                    </Col>
                  </Row>
                  <Row className={styles.filled_ingredient_row}>
                    <Col sm={4} className="mx-auto">
                      <FilledIngredient className="img-fluid" src={`data:image/png;base64,${this.props.ingredient_src}`} alt={'ingredient'} pose={this.state.progress > 0 ? 'visible' : 'hidden'} />
                    </Col>
                  </Row>
                </Col>
              </Row>
            </Col>
            <Col sm={2} className="mx-auto">
              <PosedH5 pose={this.state.hint ? 'visible' : 'hidden'}>Target: {(this.state.target / 10)+ " " + this.props.unit}</PosedH5>
              <h5>Current: {(this.state.progress / 10) + " " + this.props.unit}</h5>
              <Row className="my-3 mt-5">
                <Repeatable
                  tag={Button}
                  variant="info"
                  disabled={this.state.completed}
                  repeatDelay={0}
                  repeatInterval={150}
                  onPress={() => {
                    this.setState({
                      pressed: true,
                      progress: Math.min(this.state.progress + 10, 100),
                    })
                  }}
                  onHoldStart={() => {
                  }}
                  onHold={() => {
                  }}
                  onHoldEnd={() => {
                  }}
                  onRelease={() => {
                    this.setState({
                      pressed: false,
                    })
                  }}
                >
                  FILL
                </Repeatable>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => this.handleOpenModal()}
                >COMPLETE</Button>
                <Modal show={this.state.show_modal} onHide={() => this.handleCloseModal()}>
                  <Modal.Body>{this.getResult()}</Modal.Body>
                </Modal>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => {
                    this.setState({
                      hint: true,
                    });
                  }}
                  disabled={this.state.hint || this.state.completed}
                >GET HINT</Button>
              </Row>
            </Col>
          </Row>
        </Container>
      </Container>
    );
  }
}

export class StirGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    }
  }

  handleCloseModal() {
    this.setState({
      show_modal: false,
    })
  }

  handleOpenModal() {
    var result = (this.state.progress === this.state.target);
    this.setState({
      completed: true,
      show_modal: true,
      success: result,
    })
  }

  getResult() {
    var result = (this.state.success);
    if (result) {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Congratulations! You've successfully completed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.setup}>
              Continue
            </Button>
          </Modal.Footer>
        </div>
      );
    } else {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Failed</Modal.Title>
          </Modal.Header>
          <Modal.Body>Unfortunately, you've failed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.refresh}>
              Try Again
            </Button>
          </Modal.Footer>
        </div>
      );
    }
  }

  setup = () => {
    this.refresh();
    this.props.next();
    }

  refresh = () => {
    this.setState({
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    });
    }

  render() {
    return (
      <Container>
        <Row className="mt-5">
          <Col>
            <ProgressBar>
              <ProgressBar animated variant="info" now={this.state.progress} key={1} />
              <PosedProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} pose={this.state.hint ? 'visible' : 'hidden'} />
            </ProgressBar>
          </Col>
        </Row>
        <Container>
          <Row className="mt-5">
            <Col sm={8}>
              <Row>
                <Col sm={3} className={`mx-auto ${styles.front}`}>
                  <Spoon className={`${styles.front} img-fluid test`} src={spoon_src} alt={'spoon'} pose={this.state.progress % 2 === 0 ? 'left' : 'right'} />
                </Col>
              </Row>
              <Row className={styles.glass_stir_row}>
                <Col sm={4} className="mx-auto">
                  <img className="img-fluid" src={`data:image/png;base64,${this.props.equipment_src}`} alt={'equipment'} />
                </Col>
              </Row>
            </Col>
            <Col sm={2} className="mx-auto">
              <PosedH5 pose={this.state.hint ? 'visible' : 'hidden'}>Target: {this.state.target + " " + this.props.unit}</PosedH5>
              <h5>Current: {this.state.progress + " " + this.props.unit}</h5>
              <Row className="my-3 mt-5">
                <Repeatable
                  tag={Button}
                  variant="info"
                  disabled={this.state.completed}
                  repeatDelay={0}
                  repeatInterval={150}
                  onPress={() => {
                    this.setState({
                      pressed: true,
                    })
                  }}
                  onHoldStart={() => {
                  }}
                  onHold={() => {
                    this.setState({
                      progress: Math.min(this.state.progress + 1, 100)
                    });
                  }}
                  onHoldEnd={() => {
                  }}
                  onRelease={() => {
                    this.setState({
                      pressed: false,
                    })
                  }}
                >
                  STIR
                </Repeatable>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => this.handleOpenModal()}
                >COMPLETE</Button>
                <Modal show={this.state.show_modal} onHide={() => this.handleCloseModal()}>
                  <Modal.Body>{this.getResult()}</Modal.Body>
                </Modal>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => {
                    this.setState({
                      hint: true,
                    });
                  }}
                  disabled={this.state.hint || this.state.completed}
                >GET HINT</Button>
              </Row>
            </Col>
          </Row>
        </Container>
      </Container>
    );
  }
}

export class RollGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    }
  }

  handleCloseModal() {
    this.setState({
      show_modal: false,
    })
  }

  handleOpenModal() {
    var result = (this.state.progress === this.state.target);
    this.setState({
      completed: true,
      show_modal: true,
      success: result,
    })
  }

  getResult() {
    var result = (this.state.success);
    if (result) {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Congratulations! You've successfully completed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.setup}>
              Continue
            </Button>
          </Modal.Footer>
        </div>
      );
    } else {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Failed</Modal.Title>
          </Modal.Header>
          <Modal.Body>Unfortunately, you've failed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.refresh}>
              Try Again
            </Button>
          </Modal.Footer>
        </div>
      );
    }
  }

  setup = () => {
    this.refresh();
    this.props.next();
    }

  refresh = () => {
    this.setState({
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    });
    }

  render() {
    return (
      <Container>
        <Row className="mt-5">
          <Col>
            <ProgressBar>
              <ProgressBar animated variant="info" now={this.state.progress} key={1} />
              <PosedProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} pose={this.state.hint ? 'visible' : 'hidden'} />
            </ProgressBar>
          </Col>
        </Row>
        <Container>
          <Row className="mt-5">
            <Col sm={8}>
              <Row>
                <Col sm={3} className={`mx-auto ${styles.front}`}>
                  <Spoon className={`${styles.front} img-fluid test`} src={circle_src} alt={'side bottle'} pose={this.state.progress % 2 === 0 ? 'left' : 'right'} />
                </Col>
              </Row>
              <Row className={styles.glass_stir_row}>
                <Col sm={4} className="mx-auto">
                  <img className="img-fluid" src={`data:image/png;base64,${this.props.equipment_src}`} alt={'equipment'} />
                </Col>
              </Row>
            </Col>
            <Col sm={2} className="mx-auto">
              <PosedH5 pose={this.state.hint ? 'visible' : 'hidden'}>Target: {this.state.target + " " + this.props.unit}</PosedH5>
              <h5>Current: {this.state.progress + " " + this.props.unit}</h5>
              <Row className="my-3 mt-5">
                <Repeatable
                  tag={Button}
                  variant="info"
                  disabled={this.state.completed}
                  repeatDelay={0}
                  repeatInterval={150}
                  onPress={() => {
                    this.setState({
                      pressed: true,
                    })
                  }}
                  onHoldStart={() => {
                  }}
                  onHold={() => {
                    this.setState({
                      progress: Math.min(this.state.progress + 1, 100)
                    });
                  }}
                  onHoldEnd={() => {
                  }}
                  onRelease={() => {
                    this.setState({
                      pressed: false,
                    })
                  }}
                >
                  ROLL
                </Repeatable>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => this.handleOpenModal()}
                >COMPLETE</Button>
                <Modal show={this.state.show_modal} onHide={() => this.handleCloseModal()}>
                  <Modal.Body>{this.getResult()}</Modal.Body>
                </Modal>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => {
                    this.setState({
                      hint: true,
                    });
                  }}
                  disabled={this.state.hint || this.state.completed}
                >GET HINT</Button>
              </Row>
            </Col>
          </Row>
        </Container>
      </Container>
    );
  }
}

export class BlendGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    }
  }

  handleCloseModal() {
    this.setState({
      show_modal: false,
    })
  }

  handleOpenModal() {
    var result = (this.state.progress === this.state.target);
    this.setState({
      completed: true,
      show_modal: true,
      success: result,
    })
  }

  getResult() {
    var result = (this.state.success);
    if (result) {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Congratulations! You've successfully completed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.setup}>
              Continue
            </Button>
          </Modal.Footer>
        </div>
      );
    } else {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Failed</Modal.Title>
          </Modal.Header>
          <Modal.Body>Unfortunately, you've failed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.refresh}>
              Try Again
            </Button>
          </Modal.Footer>
        </div>
      );
    }
  }
 
  setup = () => {
    this.refresh();
    this.props.next();
    }

  refresh = () => {
    this.setState({
      progress: 0,
      target: this.props.target,
      pressed: false,
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    });
    }

  render() {
    return (
      <Container>
        <Row className="mt-5">
          <Col>
            <ProgressBar>
              <ProgressBar animated variant="info" now={this.state.progress} key={1} />
              <PosedProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} pose={this.state.hint ? 'visible' : 'hidden'} />
            </ProgressBar>
          </Col>
        </Row>
        <Container>
          <Row className="mt-5">
            <Col sm={8}>
              <Row>
                <Col sm={4} className={`mx-auto ${styles.front}`}>
                  <Blender className={`${styles.front} img-fluid test`} src={blender_src} alt={'blender'} pose={ this.state.progress % 3 === 0 ? 'one' : (this.state.progress % 3 === 1 ? 'two' : 'three') } />
                </Col>
              </Row>
            </Col>
            <Col sm={2} className="mx-auto">
              <PosedH5 pose={this.state.hint ? 'visible' : 'hidden'}>Target: {this.state.target + " " + this.props.unit}</PosedH5>
              <h5>Current: {this.state.progress + " " + this.props.unit}</h5>
              <Row className="my-3 mt-5">
                <Repeatable
                  tag={Button}
                  variant="info"
                  disabled={this.state.completed}
                  repeatDelay={0}
                  repeatInterval={150}
                  onPress={() => {
                    this.setState({
                      pressed: true,
                    })
                  }}
                  onHoldStart={() => {
                  }}
                  onHold={() => {
                    this.setState({
                      progress: Math.min(this.state.progress + 1, 100)
                    });
                  }}
                  onHoldEnd={() => {
                  }}
                  onRelease={() => {
                    this.setState({
                      pressed: false,
                    })
                  }}
                >
                  BLEND
                </Repeatable>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => this.handleOpenModal()}
                >COMPLETE</Button>
                <Modal show={this.state.show_modal} onHide={() => this.handleCloseModal()}>
                  <Modal.Body>{this.getResult()}</Modal.Body>
                </Modal>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => {
                    this.setState({
                      hint: true,
                    });
                  }}
                  disabled={this.state.hint || this.state.completed}
                >GET HINT</Button>
              </Row>
            </Col>
          </Row>
        </Container>
      </Container>
    );
  }
}

export class MatchingGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      ingredients: this.props.ingredients,
      index: Math.floor(Math.random() * this.props.length),
    }
  }

  handleCloseModal() {
    this.setState({
      show_modal: false,
    })
  }

  handleOpenModal() {
    var result = (this.props.target === this.state.ingredients[this.state.index]);
    this.setState({
      completed: true,
      show_modal: true,
      success: result,
    })
  }

  getResult() {
    var result = (this.state.success);
    if (result) {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Congratulations! You've successfully completed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.setup}>
              Continue
            </Button>
          </Modal.Footer>
        </div>
      );
    } else {
      return (
        <div>
          <Modal.Header closeButton>
            <Modal.Title>Failed</Modal.Title>
          </Modal.Header>
          <Modal.Body>Unfortunately, you've failed this step.</Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={this.refresh}>
              Try Again
            </Button>
          </Modal.Footer>
        </div>
      );
    }
  }

  setup = () => {
    this.refresh();
    this.props.next();
    }

  refresh = () => {
    this.setState({
    ingredients: this.props.ingredients,
    index: Math.floor(Math.random() * this.props.length),
      completed: false,
      success: false,
      hint: false,
      show_modal: false,
    });
    }

  render() {
    return (
      <Container>
        <Row className="mt-5">
          <Col sm={8}>
            <Row>
              <Col sm={3} className="mx-auto">
                <img className="img-fluid" src={this.props.target} alt={'target'} />
              </Col>
            </Row>
            <Row className="mt-3">
              <Col sm={3} className="mx-auto">
                <img className="img-fluid" src={this.state.ingredients[this.state.index]} alt={'ingredients'} />
              </Col>
            </Row>
          </Col>
          <Col sm={2} className="mx-auto">
            <Row className="my-3">
              <Button variant="info"
                onClick={() => {
                  this.setState({
                    index: (this.state.index + 1) % this.state.ingredients.length,
                  });
                }}
                disabled={this.state.hint || this.state.completed}
              >NEXT</Button>
            </Row>
            <Row className="my-3">
                <Button variant="info"
                  onClick={() => this.handleOpenModal()}
                >COMPLETE</Button>
                <Modal show={this.state.show_modal} onHide={() => this.handleCloseModal()}>
                  <Modal.Body>{this.getResult()}</Modal.Body>
                </Modal>
              </Row>
          </Col>
        </Row>
      </Container>
    );
  }
}