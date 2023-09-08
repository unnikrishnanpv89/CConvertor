package com.open.exchange.cconverter.presentation.utils.network

sealed class ConnectionState{
    object Available: ConnectionState()
    object Unavailable: ConnectionState()
}
