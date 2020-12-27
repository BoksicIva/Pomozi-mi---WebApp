import React from 'react';
import * as AiIcons from 'react-icons/ai';
import * as IoIcons from 'react-icons/io';
import * as BsIcons from "react-icons/bs";

export const SidebarData = [
  {
    title: 'Naslovnica',
    path: '/home',
    icon: <AiIcons.AiFillHome value={{ color: 'blue' }} />,
    cName: 'nav-text',
  },
  {
    title: 'Zahtjevi',
    path: '/page',
    icon: <IoIcons.IoIosPaper />,
    cName: 'nav-text'
  },
  {
    title: 'Profil',
    path: '/profil',
    icon: <BsIcons.BsPersonFill />,
    cName: 'nav-text'
  },
  {
    title: 'Korisnici',
    path: '/list',
    icon: <IoIcons.IoMdPeople />,
    cName: 'nav-text'
  },
  {
    title: 'Odjava',
    path: '/logout',
    icon: <IoIcons.IoMdLogOut />,
    cName: 'nav-text'
  }

];
