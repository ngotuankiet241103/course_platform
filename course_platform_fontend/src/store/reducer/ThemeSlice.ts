import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'
import { ThemeState} from '../../types/store/ThemeState'
import storage from '../../service/storage'


const initialState: ThemeState = {
  isDarkMode: storage.get("isDarkMode"),
  isExpand: storage.get("isExpand")
}

export const counterSlice = createSlice({
  name: 'theme',
  initialState,
  reducers: {
   updateThemeState: (state,action: PayloadAction<{[key: string] : boolean}>) => {
        state = {
            ...state,
            ...action.payload
        }
        return state;
   }
    
  },
})

// Action creators are generated for each case reducer function
export const { updateThemeState } = counterSlice.actions

export default counterSlice.reducer