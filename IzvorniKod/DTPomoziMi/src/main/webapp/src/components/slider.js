import React from "react";
import sliderStyle from "./style/slider.module.css";
import AliceCarousel from 'react-alice-carousel';
import "react-alice-carousel/lib/alice-carousel.css";
import image1 from '../images/dog_icon1.png'
import image2 from '../images/shoppig.png'
import image3 from '../images/hands.png'
import image4 from '../images/hands.png'

export default function App() {
  return (
    <div className="App">
     <AliceCarousel infinite touchTracking autoPlayStrategy="none" disableButtonsControls autoPlay activeIndex autoPlayInterval="3000">
      <img src={image1} className={sliderStyle.sliderimg} alt=""/>
      <img src={image2} className={sliderStyle.sliderimgShopping} alt=""/>
      <img src={image3} className={sliderStyle.sliderimg} alt=""/>
      <img src={image4} className={sliderStyle.sliderimg} alt=""/>
    </AliceCarousel>
    </div>
  );
}
