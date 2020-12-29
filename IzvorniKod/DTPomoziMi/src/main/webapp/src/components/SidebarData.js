import React from 'react';
import * as AiIcons from 'react-icons/ai';
import * as IoIcons from 'react-icons/io';
import * as BsIcons from "react-icons/bs";

export const SidebarData = [
  {
    title: 'Naslovnica',
    path: '/home',
    icon: <AiIcons.AiFillHome value={{ color: 'blue' }} />,
    cName: 'nav_text',
  },
  {
    title: 'Zahtjevi',
    path: '/page',
    icon: <IoIcons.IoIosPaper />,
    cName: 'nav_text'
  },
  {
    title: 'Profil',
    path: '/profile',
    icon: <BsIcons.BsPersonFill />,
    cName: 'nav_text'
  },
  {
    title: 'Korisnici',
    path: '/list',
    icon: <IoIcons.IoMdPeople />,
    cName: 'nav_text'
  },
  {
    title: 'Odjava',
    path: '/logout',
    icon: <IoIcons.IoMdLogOut />,
    cName: 'nav_text'
  }

];
