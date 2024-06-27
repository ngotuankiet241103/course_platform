import { configureStore } from '@reduxjs/toolkit'
import ThemeSlice from './reducer/ThemeSlice'
import { state } from '../types/store/state'

export const store = configureStore<state>({
  reducer: {
    theme: ThemeSlice
  },
})

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch