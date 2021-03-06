package org.eurofurence.connavigator.util

import android.text.Html
import android.text.Spanned
import io.swagger.client.model.Dealer
import io.swagger.client.model.EventConferenceRoom
import io.swagger.client.model.EventEntry
import io.swagger.client.model.Info
import org.eurofurence.connavigator.database.Database
import org.eurofurence.connavigator.util.extensions.get
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

/**
 * Created by David on 11-5-2016.
 */
object Formatter {
    val splitter_1 = "–"
    val splitter_2 = "—"

    /*
    Formats an event starting and ending times to conform to our standards
     */
    fun eventToTimes(eventEntry: EventEntry, database: Database, short: Boolean): Spanned {
        val date: String = if (short) {
            DateTime(database.eventConferenceDayDb.keyValues[eventEntry.conferenceDayId]!!.date).dayOfWeek().asText
        } else {
            DateTime(database.eventConferenceDayDb[eventEntry.conferenceDayId]!!.date).toString(DateTimeFormat.forPattern("MMM d"))
        }
        val string = "<b>%s</b> from <b>%s</b> to <b>%s</b>".format(
                date,
                eventEntry.startTime.subSequence(0, 5),
                eventEntry.endTime.subSequence(0, 5)
        )

        return Html.fromHtml(string)
    }

    fun shortTime(string: String, date: DateTime? = null) =
            if (date == null) {
                string.subSequence(0, 5).toString()
            } else {
                string.subSequence(0, 5).toString() + " ${date.toString("EE")}"
            }

    /*
    Formats an event title accoding to our rules
     */
    fun eventTitle(eventEntry: EventEntry): Spanned {
        if (eventEntry.subTitle.isNullOrEmpty()) {
            return formatHTML(eventEntry.title)
        } else {
            val format = if (eventEntry.isDeviatingFromConBook.toInt() == 0) {
                (eventEntry.title).toString().trim() + ":<br /> <i>${eventEntry.subTitle}</i>"
            } else {
                (eventEntry.title).toString().trim() + ":<br /> <i>${eventEntry.subTitle}</i><br />This item differs from the conbook!"
            }
            return Html.fromHtml(format)
        }
    }

    fun dealerName(dealer: Dealer): String {
        if (dealer.displayName != "")
            return "${dealer.displayName}"
        else
            return dealer.attendeeNickname
    }

    /*
        Formats the full room using an html element
     */
    fun roomFull(room: EventConferenceRoom): Spanned {
        return formatHTML(room.name)
    }

    /*
    Get's the room name that we assign
     */
    fun roomName(room: EventConferenceRoom): String {
        return split(room.name)[0]
    }

    fun eventOwner(eventEntry: EventEntry): Spanned {
        return Html.fromHtml("Hosted by <i>%s</i>".format(eventEntry.panelHosts))
    }

    /*
    Takes an input and formats it as html
     */
    private fun formatHTML(string: String): Spanned {
        val title_split = split(string)
        val html = when (title_split.count()) {
            2 -> {
                "%s: <i>%s</i>".format(
                        title_split[0].trim(),
                        title_split[1]
                )
            }
            else -> title_split[0]
        }

        return Html.fromHtml(html)
    }

    /*
    Splits according to our splitters
     */
    private fun split(string: String): List<String> {
        val title_split = string.split(splitter_1, splitter_2)
        return title_split
    }

    fun shareEvent(eventEntry: EventEntry): String {
        return "Check out ${eventEntry.title}!\n${createUrl("event", eventEntry.id)}"
    }

    fun shareDealer(dealer: Dealer): String {
        return "Check out ${dealer.displayName} at the dealers den!\n${createUrl("dealer", dealer.id)}"
    }

    fun shareInfo(info: Info): String {
        return "Hey, this might help: ${info.title}!\n${createUrl("info", info.id)}"
    }

    fun createUrl(type: String, id: UUID): String {
        return "https://app.eurofurence.org/web/#/$type/${id.toString()}"
    }

    fun wikiToMarkdown(text: String): Spanned =
            Html.fromHtml(text.replace("\\\\", "<br>")
                    .replace("\n\n", "<br><br>")
                    .replace(Regex("^([^ ]+.*$)(\\n^)(  \\*)", RegexOption.MULTILINE), "$1<br><br>$2$3")
                    .replace(Regex("(^  \\*[^\\n]+$\\n^)(?!  \\* )", RegexOption.MULTILINE), "$1<br><br>")
                    .replace(Regex("^  \\* (.*)$", RegexOption.MULTILINE), "&nbsp;&nbsp;&nbsp;&nbsp; &bull; $1 <br/>")
                    .replace(Regex("\\*\\*([^\\*]*)\\*\\*"), "<b>$1</b>")
                    .replace(Regex("\\*([^\\*]*)\\*"), "<i>$1</i>"))

}