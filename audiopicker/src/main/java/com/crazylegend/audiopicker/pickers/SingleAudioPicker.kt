package com.crazylegend.audiopicker.pickers

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.consts.SINGLE_PICKER_BOTTOM_SHEET
import com.crazylegend.audiopicker.consts.SINGLE_PICKER_DIALOG
import com.crazylegend.audiopicker.dialogs.single.SingleAudioPickerBottomSheetDialog
import com.crazylegend.audiopicker.dialogs.single.SingleAudioPickerDialogFragment
import com.crazylegend.audiopicker.listeners.onAudioDSL
import com.crazylegend.audiopicker.modifiers.SingleAudioPickerModifier
import com.crazylegend.extensions.setupManager


/**
 * Created by crazy on 5/12/20 to long live and prosper !
 */
object SingleAudioPicker {

    fun restoreListener(context: Context, onPickedAudio: (audio: AudioModel) -> Unit) {
        val manager = context.setupManager()
        when (val fragment = manager.findFragmentByTag(SINGLE_PICKER_BOTTOM_SHEET)
                ?: manager.findFragmentByTag(SINGLE_PICKER_DIALOG)) {
            is SingleAudioPickerBottomSheetDialog -> {
                fragment.onAudioPicked = onAudioDSL(onPickedAudio)
            }
            is SingleAudioPickerDialogFragment -> {
                fragment.onAudioPicked = onAudioDSL(onPickedAudio)
            }
            null -> {
                Log.e(SingleAudioPicker::class.java.name, "FRAGMENT NOT FOUND")
            }
        }
    }

    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun bottomSheetPicker(context: Context, pickerModifier: SingleAudioPickerModifier.() -> Unit = {}, onPickedAudio: (audio: AudioModel) -> Unit) {
        val modifier = setupModifier(pickerModifier)
        val manager = context.setupManager()
        with(SingleAudioPickerBottomSheetDialog()) {
            addModifier(modifier)
            onAudioPicked = onAudioDSL(onPickedAudio)
            show(manager, SINGLE_PICKER_BOTTOM_SHEET)
        }
    }

    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun dialogPicker(context: Context, pickerModifier: SingleAudioPickerModifier.() -> Unit = {}, onPickedAudio: (audio: AudioModel) -> Unit) {
        val manager = context.setupManager()
        val modifier = setupModifier(pickerModifier)
        with(SingleAudioPickerDialogFragment()) {
            addModifier(modifier)
            onAudioPicked = onAudioDSL(onPickedAudio)
            show(manager, SINGLE_PICKER_DIALOG)
        }
    }

    private fun setupModifier(audioPicker: SingleAudioPickerModifier.() -> Unit): SingleAudioPickerModifier {
        val modifier = SingleAudioPickerModifier()
        modifier.audioPicker()
        return modifier
    }
}