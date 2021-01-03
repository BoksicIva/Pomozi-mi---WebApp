import React from "react";
import homeStyle from "./style/home.module.css";

import Sidebar from "./sidebar";
import Slideshow from "./slider";

export default function home(props) {
  return (
    <>
      <Sidebar />
      <img src="../images/holding-hands.png" alt="" />
      <div className={homeStyle.container}>
        <div className={homeStyle.pomozi}>Pomozi mi</div>
        <div className={homeStyle.msg}>
          Malo djelo velikog<span className={homeStyle.znacaja}> značaja</span>.
        </div>
      </div>
      <Slideshow />
    </>
  );
}
