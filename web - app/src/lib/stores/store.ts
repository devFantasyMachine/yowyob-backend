import { configureStore } from '@reduxjs/toolkit'
import {tenantSlice} from './features/tenants/tenantSlice'
import {userSlice} from './features/users/userSlice'
import {authSlice} from './features/auth/authSlice'
import {appSlice} from './features/apps/appSlice'

export const makeStore = () => {
  return configureStore({
    reducer: {
      [appSlice.name]: appSlice.reducer,
      [tenantSlice.name]: tenantSlice.reducer,
      [userSlice.name]: userSlice.reducer,
      [authSlice.name]: authSlice.reducer
    }
  })
}

// Infer the type of makeStore
export type AppStore = ReturnType<typeof makeStore>
// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<AppStore['getState']>
export type AppDispatch = AppStore['dispatch']

