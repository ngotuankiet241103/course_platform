import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.scss'
import { QueryClient, QueryClientProvider } from 'react-query'
import {BrowserRouter} from 'react-router-dom' 
import { Provider } from 'react-redux'
import { store } from './store/store.ts'
const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <Provider store={store}>

    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </QueryClientProvider>
    </Provider>
   
  </React.StrictMode>,
)
