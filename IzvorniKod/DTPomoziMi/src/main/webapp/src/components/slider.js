import React from "react";
import "./style/slider.css";
import AliceCarousel from 'react-alice-carousel';
import "react-alice-carousel/lib/alice-carousel.css";
import image1 from '../images/hands.png'
import image2 from '../images/hands.png'
import image3 from '../images/hands.png'
import image4 from '../images/hands.png'

export default function App() {
  return (
    <div className="App">
     <AliceCarousel autoPlay activeIndex autoPlayInterval="3000">
      <img src={image1} className="sliderimg" alt=""/>
      <img src={image2} className="sliderimg" alt=""/>
      <img src={image3} className="sliderimg" alt=""/>
      <img src={image4} className="sliderimg" alt=""/>
    </AliceCarousel>
    </div>
  );
}
