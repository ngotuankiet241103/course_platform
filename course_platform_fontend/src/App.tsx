import { useState } from 'react'
import './App.css'
import { Route, Routes } from 'react-router-dom'
import DashboardScreen from './pages/dashboard/DashboardScreen'
import DashboardLayout from './layout/dashboard/DashboardLayout'
import CoursersScreen from './pages/dashboard/CoursersScreen'

function App() {

  return (
    <>
     <Routes>
  
        <Route path='/admin' element={<DashboardLayout/>}>
          <Route path='/admin/dashboard' element={<DashboardScreen></DashboardScreen>}></Route>
          <Route path='/admin/courses' element={<CoursersScreen></CoursersScreen>}></Route>
        </Route>
      
  
      </Routes>
    </>
  )
}

export default App
