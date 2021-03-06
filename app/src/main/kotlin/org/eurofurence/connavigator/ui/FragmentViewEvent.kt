package org.eurofurence.connavigator.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.swagger.client.model.EventEntry
import org.eurofurence.connavigator.R
import org.eurofurence.connavigator.database.Database
import org.eurofurence.connavigator.net.imageService
import org.eurofurence.connavigator.tracking.Analytics
import org.eurofurence.connavigator.ui.dialogs.EventDialog
import org.eurofurence.connavigator.util.EventFavouriter
import org.eurofurence.connavigator.util.Formatter
import org.eurofurence.connavigator.util.delegators.view
import org.eurofurence.connavigator.util.extensions.contains
import org.eurofurence.connavigator.util.extensions.get
import org.eurofurence.connavigator.util.extensions.jsonObjects
import org.eurofurence.connavigator.util.extensions.letRoot
import us.feras.mdv.MarkdownView

/**
 * Created by David on 4/9/2016.
 */
class FragmentViewEvent() : Fragment() {
    companion object {
        val EVENT_STATUS_CHANGED = "org.eurofurence.connavigator.ui.EVENT_STATUS_CHANGED"
    }

    /**
     * Constructs the info view with an assigned bundle
     */
    constructor(eventEntry: EventEntry) : this() {
        arguments = Bundle()
        arguments.jsonObjects["eventEntry"] = eventEntry
    }

    val title by view(TextView::class.java)
    val description by view(MarkdownView::class.java)
    val image by view(ImageView::class.java)
    val organizers by view(TextView::class.java)
    val room by view(TextView::class.java)
    val time by view(TextView::class.java)
    val buttonSave by view(FloatingActionButton::class.java)

    val preferences: SharedPreferences get() = letRoot { it.preferences }!!

    val database: Database get() = letRoot { it.database }!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fview_event, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Analytics.screen("Event Specific")


        if ("eventEntry" in arguments) {
            val eventEntry = arguments.jsonObjects["eventEntry", EventEntry::class.java]

            Analytics.event(Analytics.Category.EVENT, Analytics.Action.OPENED, eventEntry.title)

            val conferenceRoom = database.eventConferenceRoomDb.keyValues[eventEntry.conferenceRoomId]
            val conferenceDay = database.eventConferenceDayDb.keyValues[eventEntry.conferenceDayId]

            title.text = Formatter.eventTitle(eventEntry)

            description.loadMarkdown(eventEntry.description)

            time.text = Formatter.eventToTimes(eventEntry, database, preferences.getBoolean(context.getString(R.string.date_short), true))
            organizers.text = Formatter.eventOwner(eventEntry)
            room.text = Formatter.roomFull(conferenceRoom!!)

            imageService.load(database.imageDb[eventEntry.imageId], image)

            changeFabIcon(eventEntry)

            buttonSave.setOnClickListener {
                EventDialog(eventEntry).show(activity.supportFragmentManager, "Event Dialog").let { true }
            }

            buttonSave.setOnLongClickListener {
                if (EventFavouriter(context).toNotifications(eventEntry)) {
                    Snackbar.make(buttonSave, "Favorited this event!", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(buttonSave, "Removed this event from favorites!", Snackbar.LENGTH_SHORT).show()
                }

                changeFabIcon(eventEntry)

                true
            }
        }
    }

    /**
     * Changes the FAB based on if the current event is liked or not
     */
    private fun changeFabIcon(eventEntry: EventEntry?) {
        if (database.favoritedDb.items.contains(eventEntry))
            buttonSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_like_filled))
        else
            buttonSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_menu))
    }

}