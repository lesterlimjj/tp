---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# MatchEstate User Guide

MatchEstate is a **desktop app for real estate agents to efficiently manage and track buyers and sellers. It is optimized for fast keyboard-based input, allowing real estate professionals to handle transactions quickly. If you can type fast, MatchEstate can get your management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103-F08-3/tp/releases/tag/v1.3).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar matchestate.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Person Management

#### Adding a person: `addPerson`
Adds a person to the address book.

Format: `addPerson n/NAME p/PHONE e/EMAIL`

Examples:
* `addPerson n/John Doe p/98765432 e/johnd@example.com`

#### Listing all persons: `listPerson`
Shows a list of all persons in the address book and removes any filters that are currently applied.

Format: `listPerson`

#### Editing a person: `editPerson`
Edits an existing person in the address book.

Format: `editPerson INDEX [n/NAME] [p/PHONE] [e/EMAIL]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `editPerson 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.

#### Locating persons by name: `findPerson`

Finds persons whose names contain any of the given keywords.

Format: `findPerson KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `findPerson John` returns `john` and `John Doe`
* `findPerson alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

#### Searching persons by preferences: `searchPerson`
Finds persons with property preferences containing all specified tags.

Format: `searchPerson t/TAG [t/TAG]...`

Example:
* `searchPerson t/gym t/pet-friendly` returns all persons who have both `gym` and `pet-friendly` tags in their property preference.

#### Deleting a person : `deletePerson`

Deletes the specified person from the address book.

Format: `deletePerson INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `listListing` followed by `deletePerson 2` deletes the 2nd person in the address book.
* `findPerson Betsy` followed by `deletePerson 1` deletes the 1st person in the results of the `findPerson` command.

### Listing Management

#### Adding a listing: `addListing`
Adds a property listing to the address book.

Format: `addListing pc/POSTAL_CODE [u/UNIT_NUMBER] [h/HOUSE_NUMBER] [lbp/LOWER_BOUND_PRICE] [ubp/UPPER_BOUND_PRICE] [n/PROPERTY_NAME] [t/TAG]... [nt/NEW_TAG]...`

Example:
* `addListing pc/654321 h/12 lbp/300000 ubp/600000 n/Sunny Villa t/quiet t/pet-friendly nt/family-friendly nt/spacious`

<box type="tip" seamless>
**Tip:** A listing can have any number of tags (including 0)
</box>

#### Listing all properties: `listListing`
Shows a list of all property listings and removes any filters that are currently applied.

Format: `listListing`

#### Searching properties: `searchProperty`
Finds properties with all specified tags.

Format: `searchProperty t/TAG [t/TAG]...`

Example:
* `searchProperty t/pet-friendly t/pool`

#### Searching owner properties: `searchOwnerProperty`
Finds properties owned by a specific person.

Format: `searchOwnerProperty PERSON_INDEX`

Example:
* `searchOwnerProperty 1`

#### Marking listing availability:
- `markAvailable`: Marks listing as available
- `markUnavailable`: Marks listing as unavailable

Format: `markAvailable INDEX` or `markUnavailable INDEX`

Listings marked as unavailable will not be matched to any person.

Example:
* `markAvailable 1`
* `markUnavailable 2`

#### Deleting a listing: `deleteListing`
Deletes the specified listing from the address book.

Format: `deleteListing INDEX`

Example:
* `deleteListing 1`

### Tag Management

#### Adding tags: `addTag`
Adds new tags to the system.

Format: `addTag [nt/NEW_TAG]...`

Example:
* `addTag nt/family-friendly nt/spacious`

#### Listing all tags: `listTag`
Shows a list of all available tags and removes any filters that are currently applied.

Format: `listTag`

#### Deleting tags: `deleteTag`
Deletes the specified tags from the system.

Format: `deleteTag [t/TAG]...`

Example:
* `deleteTag t/quiet t/pet-friendly`

### Preference Management

#### Adding a preference: `addPreference`
Adds a property preference to a person.

Format: `addPreference INDEX lbp/LOWER_BOUND_PRICE ubp/UPPER_BOUND_PRICE [t/TAG]... [nt/NEW_TAG]...`

Example:
* `addPreference 2 lbp/300000 ubp/600000 t/quiet t/pet-friendly nt/family-friendly nt/spacious`

<box type="tip" seamless>
**Tip:** A preference can have any number of tags (including 0)
</box>

#### Adding tags to a preference: `addPreferenceTag`
Adds tags to an existing preference.

Format: `addPreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]... [nt/NEW_TAG]...`

Example:
* `addPreferenceTag 2 1 t/quiet t/pet-friendly nt/family-friendly nt/spacious`

#### Overwriting preference tags: `overwritePreferenceTag`
Replaces all tags in an existing preference.

Format: `overwritePreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]... [nt/NEW_TAG]...`

Example:
* `overwritePreferenceTag 3 2 t/2-bedrooms nt/seaside-view`

#### Deleting a preference: `deletePreference`
Deletes a person's property preference.

Format: `deletePreference PERSON_INDEX PREFERENCE_INDEX`

Example:
* `deletePreference 1 2`

#### Deleting preference tags: `deletePreferenceTag`
Deletes tags from a person's preference.

Format: `deletePreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]...`

Example:
* `deletePreferenceTag 3 1 t/pet-friendly t/pool`

### Property Tag Management

#### Adding tags to listing: `addListingTag`
Adds tags to a property listing.

Format: `addListingTag INDEX [t/TAG]... [nt/NEW_TAG]...`

Example:
* `addListingTag 2 t/quiet t/pet-friendly nt/family-friendly nt/spacious`

#### Overwriting property tags: `overwritePropertyTag`
Replaces all tags in a property listing.

Format: `overwritePropertyTag PROPERTY_INDEX [t/TAG]... [nt/NEW_TAG]...`

Example:
* `overwritePropertyTag 3 t/4-bedrooms t/2-toilets nt/seaside-view`

#### Deleting property tags: `deletePropertyTag`
Deletes tags from a property.

Format: `deletePropertyTag PROPERTY_INDEX [t/TAG]...`

Example:
* `deletePropertyTag 3 t/pet-friendly t/pool`

### Matching System

#### Matching persons to listings: `matchPerson`
Finds listings matching a person's preference.

Format: `matchPerson PERSON_INDEX PREFERENCE_INDEX`

Example:
* `matchPerson 1 2`

Listings that are marked as unavailable or owned by the person will not be shown.

#### Matching listings to persons: `matchProperty`
Finds persons whose preferences match a listing.

Format: `matchProperty INDEX`

Example:
* `matchProperty 1`

### Owner Management

#### Assigning an owner to a person: `assignOwner`
Assigns an owner to a listing.

Format: `assignListing PERSON_INDEX LISTING_INDEX`

Example:
* `assignOwner 2 1`

#### Deleting an owner: `deleteOwner`
Removes an owner from a listing.

Format: `deleteOwner LISTING_INDEX OWNER_INDEX`

Example:
* `deleteOwner 1 2`

### General Commands

#### Clearing all data: `clear`
Clears all entries from the address book.

Format: `clear`

#### Exiting the program: `exit`
Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
