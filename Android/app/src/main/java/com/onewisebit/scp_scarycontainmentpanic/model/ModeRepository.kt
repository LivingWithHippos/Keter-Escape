package com.onewisebit.scp_scarycontainmentpanic.model

class ModeRepository(private val modeDAO:ModeDAO):InModeRepository {
    override fun getMode(id: Int): Mode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertMode(mode: Mode) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllModes(): List<Mode> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertAll(modes: List<Mode>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}