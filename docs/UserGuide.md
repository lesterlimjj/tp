---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# MatchEstate User Guide

<<<<<<< HEAD
MatchEstate is a **desktop app for real estate agents to efficiently manage and track buyers and sellers. It is optimized for fast keyboard-based input, allowing real estate professionals to handle transactions quickly. If you can type fast, MatchEstate can get your management tasks done faster than traditional GUI apps.
=======
**MatchEstate** is a desktop application tailored for **real estate agents** to efficiently manage their clients — whether they are buyers, sellers, or both. MatchEstate enables fast matching of buyers to suitable listings and vice versa. While it features a graphical interface, the app is optimized for users who prefer fast keyboard-based interactions using a Command Line Interface (CLI).

MatchEstate classifies contacts into two main roles:

**Buyers** are clients looking to purchase a property. They typically have a set of criteria such as budget, location, property type (e.g., HDB, Condo), or features like “near MRT” or “near schools”.
- These are captured in property preferences — each containing a price range and a list of tags representing specific requirements.
- A buyer can have multiple preferences, as they may be looking to purchase more than one property.


**Sellers** are clients looking to list properties for sale.
- Each listing contains essential property details such as postal code, unit number as well as the asking price, and a set of tags to describe its specifications for matching.
- To associate a listing with a seller, agents can simply add the listing to a person, establishing ownership.

#### Automatic Classification:
MatchEstate automatically classifies people based on their data:
- A person is a buyer if they have at least one property preference.
- A person is a seller if they have at least one listing.

💡 A person can be both a buyer and a seller in the system.
![matchEstate](images/CS2103UG/matchEstate.png)
>>>>>>> master

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

    * `listPerson` : Lists all persons.

    * `listListing` : Lists all listings.

    * `addPerson n/John Doe p/98765432 e/john@example.com` : Adds a person named `John Doe`.

    * `addTag nt/pool nt/near MRT` : Adds the tags `pool` and `near MRT`.

    * `addListing pc/654321 u/10-12 lbp/1000000 ubp/2000000 n/Sunny Villa nt/big` : Adds a listing with postal code `654321`, unit number `10-12`, price range `$1000000 - $2000000`, name `Sunny Villa` and tag `big` .

    * `deletePerson 3` : Deletes the 3rd person shown in the current persons list.

    * `deleteListing 1` : Deletes the 1st listing shown in the current listings list.

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


* Items in brackets are mutually exclusive.<br>
  e.g. `pc/POSTAL_CODE (u/UNIT_NUMBER)(h/HOUSE_NUMBER)` can be used as `pc/654321 u/12-111` or as `pc/654321 h/12` but not both.


* Items with `…`​ after them can be used multiple times but at least one time.<br>
  e.g. `t/TAG…​` can be used as `t/friend`, `t/friend t/family` etc.


* Items in square brackets with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.


* Items in square brackets with a {num} after them, groups 2 or more prefixes in the command which requires at least 1 prefix from the group to be present.<br>
  e.g. `[t/TAG]{1}... [nt/NEW_TAG]{1}...` can be used as `nt/friend`, `t/family`, `t/family nt/friend` but not ` `(no parameter specified).


* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.


* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.


* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</box>

### Command Summary

#### General Commands
| Command   | Description                                             | Usage |
|-----------|---------------------------------------------------------|-------|
**Help** | Displays all command usage on a second window |`help`
**Clear** | Clears all entries from the MatchEstate                 |`clear`
**Exit** | Exits the program                                       |`exit`

#### Person Management

<<<<<<< HEAD
| Command   | Description                                                        | Usage |
|-----------|--------------------------------------------------------------------|-------|
**Add Person** | Adds a person to matchEstate.                                     |`addPerson n/NAME p/PHONE e/EMAIL`
**List Persons** | Shows a list of all persons in matchEstate                   |`listPerson`
**Edit Person** | Edits an existing person in matchEstate                      |`editPerson PERSON_INDEX [n/NAME]{1} [p/PHONE]{1} [e/EMAIL]{1}`
**Search Person by Name** | Finds persons whose names match the given keyword(s)              |`searchPersonName KEYWORD [MORE_KEYWORDS]`
**Search Person by Tags** | Finds persons with property preferences containing all specified tag(s) |`searchPersonTag t/TAG...`
**Delete Person**  | Deletes the specified person from the matchEstate                |`deletePerson PERSON_INDEX`
=======
| Command   | Description                                                       | Usage |
|-----------|-------------------------------------------------------------------|-------|
**Add Person** | Adds a person to MatchEstate.                                    |`addPerson n/NAME p/PHONE e/EMAIL`
**List Persons** | Shows a list of all persons in MatchEstate                   |`listPerson`
**Edit Person** | Edits an existing person in MatchEstate                      |`editPerson PERSON_INDEX [n/NAME]{1} [p/PHONE]{1} [e/EMAIL]{1}`
**Search Person by Name** | Finds persons whose names match the given keyword(s)             |`searchPersonName KEYWORD [MORE_KEYWORDS]`
**Search Person by Tags** | Finds persons with property preferences containing all specified tag(s) |`searchPersonTag t/TAG...`
**Delete Person**  | Deletes the specified person from MatchEstate                |`deletePerson PERSON_INDEX`
>>>>>>> master

### Listing Management

| Command   | Description                                     | Usage |
|-----------|-------------------------------------------------|-------|
**Add Listing** | Adds a listing to MatchEstate.             |`addListing pc/POSTAL_CODE (u/UNIT_NUMBER)(h/HOUSE_NUMBER) [lbp/LOWER_BOUND_PRICE] [ubp/UPPER_BOUND_PRICE] [n/PROPERTY_NAME] [t/TAG]... [nt/NEW_TAG]...`
**List Listings** | Shows a list of all property listings          |`listListing`
**Search Listings by Tags** | Finds listings with all specified tags         |`searchListingTag t/TAG...`
**Search Owner’s Listings**  | Finds listings owned by a specific person      |`searchOwnerListing PERSON_INDEX`
**Mark Available**| Marks Listing as available                     |`markAvailable LISTING_INDEX`
**Mark Unavailable** | Marks listing as unavailable                   |`markUnavailable LISTING_INDEX`
**Delete Listing** | Deletes the specified listing from MatchEstate |`deleteListing LISTING_INDEX`

### Tag Management

| Command   | Description                                   | Usage |
|-----------|-----------------------------------------------|-------|
**Add Tags** | Adds new tags to the system            |`addTag nt/NEW_TAG...`
**Delete Tags** | Deletes the specified tags from the system       |`deleteTag t/TAG...`

### Preference Management

| Command   | Description                                   | Usage |
|-----------|-----------------------------------------------|-------|
**Add Preference**  | Adds a property preference to a person          |`addPreference PERSON_INDEX [lbp/LOWER_BOUND_PRICE] [ubp/UPPER_BOUND_PRICE] [t/TAG]... [nt/NEW_TAG]...`
**Add Preference Tags**  | Adds tags to an existing preference          |`addPreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]{1}... [nt/NEW_TAG]{1}...`
**Overwrite Preference Tags** | Replaces all tags in an existing preference     |`overwritePreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]{1}... [nt/NEW_TAG]{1}...`
**Delete Preference**  | Deletes a person's property preference        |`deletePreference PERSON_INDEX PREFERENCE_INDEX`
**Delete Preference Tags** | Deletes tags from a person's preference       |`deletePreferenceTag PERSON_INDEX PREFERENCE_INDEX t/TAG...`

### Listing Tag Management
| Command   | Description                     | Usage |
|-----------|---------------------------------|-------|
**Add Listing Tags**  | Adds tags to a listing         |`addListingTag LISTING_INDEX [t/TAG]{1}... [nt/NEW_TAG]{1}...`
**Overwrite Listing Tags**  | Replaces all tags in a listing |`overwriteListingTag LISTING_INDEX [t/TAG]{1}... [nt/NEW_TAG]{1}...`
**Delete Listing Tags** | Deletes tags from a listing    |`deleteListingTag LISTING_INDEX t/TAG...`

### Matching System
| Command   | Description                     | Usage |
|-----------|---------------------------------|-------|
**Match Person's Preference to Listings**  | Finds listings matching a person's preference        |`matchPreference PERSON_INDEX PREFERENCE_INDEX`
**Match Listing to Persons**   | Finds persons whose preferences match a listing |`matchListing LISTING_INDEX`

### Listing Owner Management
| Command   | Description                          | Usage |
|-----------|--------------------------------------|-------|
**Add Owner**  | Adds a person as owner to a listing |`addOwner PERSON_INDEX LISTING_INDEX`
**Delete Owner**   | Removes an owner from a listing     |`deleteOwner LISTING_INDEX OWNER_INDEX`


### General Commands

#### Viewing help : `help`

Opens a second window displaying all the command usages and explaining how to access the user guide.

Format: `help`

Result:
![help message](images/helpMessage.png)

#### Clearing all data: `clear`
Clears all entries from the address book.

Format: `clear`

#### Exiting the program: `exit`
Exits the program.

Format: `exit`

### Saving the data

MatchEstate data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

MatchEstate data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, MatchEstate will save a copy of the invalid file and start with an empty data file at the next run.  However, it is recommended to make a backup of the file before editing it.<br>
Furthermore, certain edits can cause the MatchEstate to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

### Person Management

#### Adding a person: `addPerson`
<<<<<<< HEAD
Adds a person to the address book.

Format: `addPerson n/NAME p/PHONE e/EMAIL`

=======
Adds a person to MatchEstate .

Format: `addPerson n/NAME p/PHONE e/EMAIL`

Input restriction:
* `NAME` must start with a letter. 
* `NAME` must only contain 2-60 characters that allow spaces, hyphens, full stops, or apostrophes.
* `PHONE` must be between 3-15 digits. The digits can be prefixed with a `+` sign. 
* `PHONE` must also be unique since it is used to unique identify a person.
* `EMAIL` must be in a format with local-part and domain name, like `name@domain` or `name@domain.com`.
    * local-part must only contain alphanumeric characters and these special characters, `+_.-`.
    * local-part must not start or end with any special characters.
    * domain name contains domain labels separated by `.` or `-`.
    * each domain name must end with a domain label of 2 characters long
    * each domain label must contain alphanumeric characters

<box type="info" seamless>

Note on uniquely identifying Person:

- `Phone` is used to uniquely identify a person, since phone numbers are a commonly used as a direct and personal means of contact. 

- In contrast, `Email` is primarily used to send any legal documents regarding the transaction and thus may often use a shared organizational email. As such, duplicate phone numbers are disallowed to prevent ambiguity, while duplicate emails are permitted.

- `Name` represents the client's name for a real estate agent to address by, and does not need to be a full legal name. Additionally since multiple people may share the same name, duplicates are allowed.

</box>

>>>>>>> master
Examples:
* `addPerson n/John Doe p/98765432 e/johnd@example.com`
* `addPerson n/Betty Smith p/+6598274892 e/bettysmith@abc`
* `addPerson n/Bob Parker p/934 e/bobparker@example.com`

#### Listing all persons: `listPerson`
<<<<<<< HEAD
Shows a list of all persons in the address book.
=======
Shows a list of all persons in the MatchEstate .
>>>>>>> master

Format: `listPerson`

#### Editing a person: `editPerson`
<<<<<<< HEAD
Edits an existing person in the address book.

Format: `editPerson PERSON_INDEX [n/NAME]{1} [p/PHONE]{1} [e/EMAIL]{1}`

* Edits the person at the specified `PERSON_INDEX`. The index refers to the index number shown in the displayed person list. The index must be a positive integer.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
=======
Edits an existing person in the MatchEstate.

Format: `editPerson PERSON_INDEX [n/NAME]{1} [p/PHONE]{1} [e/EMAIL]{1}`

Input restriction:
* `PERSON_INDEX` must be a positive integer within the bounds of the person list.
* `NAME` must start with a letter.
* `NAME` must only contain 2-60 characters that allow spaces, hyphens, full stops, or apostrophes.
* `PHONE` must be between 3-15 digits. The digits can be prefixed with a `+` sign.
* `PHONE` must also be unique since it is used to unique identify a person.
* `EMAIL` must be in a format with local-part and domain name, like `name@domain` or `name@domain.com`.
    * local-part must only contain alphanumeric characters and these special characters, `+_.-`.
    * local-part must not start or end with any special characters.
    * domain name contains domain labels separated by `.` or `-`.
    * each domain name must end with a domain label of 2 characters long
    * each domain label must contain alphanumeric characters

<box type="info" seamless>

Note on uniquely identifying Person:

- `Phone` is used to uniquely identify a person, since phone numbers are a commonly used as a direct and personal means of contact.

- In contrast, `Email` is primarily used to send any legal documents regarding the transaction and thus may often use a shared organizational email. As such, duplicate phone numbers are disallowed to prevent ambiguity, while duplicate emails are permitted.

- `Name` represents the client's name for a real estate agent to address by, and does not need to be a full legal name. Additionally since multiple people may share the same name, duplicates are allowed.

</box>
>>>>>>> master

Examples:
* `editPerson 1 n/Betty Smith` Edits the name of the 1st person.
* `editPerson 2 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 2nd person.
* `editPerson 3 n/Bob Parker p/+6581008383 e/bobparker@example.com` Edits the name, phone number and email address of the 3rd person.

#### Searching persons by name: `searchPersonName`
Finds persons whose names match the given keyword(s).

Format: `searchPersonName KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive.
<<<<<<< HEAD
* Keywords must contain only letters, spaces, hyphens, full stops, or apostrophes.
* Every keyword can only start with a letter.
=======
* `KEYWORD` and `MORE_KEYWORDS` must contain only letters, hyphens, full stops, or apostrophes.
* Every `KEYWORD` and `MORE_KEYWORDS` can only start with a letter.
>>>>>>> master

> ⚠️ **Note:** Each new search command will override the results of the previous search.  
For example, if you perform a `searchListingTag` followed by another `searchOwnerListing`, only the results from the second `searchOwnerListing` will be applied to all data. The filters do not stack.

Examples:
* `searchPersonName John` returns persons with names matching "John".
* `searchPersonName John Doe` returns persons with names matching "John" or "Doe".
* `searchPersonName John Doe Bob` returns persons with names matching "John", "Doe" or "Bob".

#### Searching persons by preferences: `searchPersonTag`
Finds persons with at least one property preference containing all specified tags.

<<<<<<< HEAD
Format: `searchPersonTag t/TAG [t/TAG]...`

Example:
* `searchPersonTag t/gym` returns all persons who have at least one property preference containing the `gym` tag
* `searchPersonTag t/gym t/pet-friendly` returns all persons who have at least one property preference containing both `gym` and `pet-friendly` tags.

#### Deleting a person: `deletePerson`
Deletes the specified person from the address book.
=======
* After
<br>![searchPersonNameAfter](images/CS2103UG/searchPersonNameAfter.png)

#### Searching persons by tags: `searchPersonTag`
Finds persons with property preferences containing all specified tags.

Format: `searchPersonTag t/TAG...`

Input restriction:
* The search is case-insensitive.
* `TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands. 
* The tag cannot be blank and must already exist.

> ⚠️ **Note:** Each new search command will override the results of the previous search.  
For example, if you perform a `searchListingTag` followed by another `searchOwnerListing`, only the results from the second `searchOwnerListing` will be applied to all data. The filters do not stack.

Example:
* `searchPersonTag t/gym t/pet-friendly` returns all persons **who have at least one property preference that contains ALL the specified tags (`gym` and `pet-friendly`)**.

Result:
* Before
<br>![searchPersonTagBefore](images/CS2103UG/searchPersonTagBefore.png)

* After
<br>![searchPersonTagAfter](images/CS2103UG/searchPersonTagAfter.png)

#### Deleting a person: `deletePerson`
Deletes the specified person from MatchEstate, along with their property preferences and ownerships of listing.
>>>>>>> master

Format: `deletePerson PERSON_INDEX`

* Deletes the person at the specified `PERSON_INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index must be a positive integer.

Examples:
<<<<<<< HEAD
* `deletePerson 2` deletes the 2nd person in the address book.
=======
* `deletePerson 2` deletes the 2nd person in MatchEstate.

Result:
* Before
<br>![deletePersonBefore](images/CS2103UG/deletePersonBefore.png)

* After
<br>![deletePersonAfter](images/CS2103UG/deletePersonAfter.png)
>>>>>>> master

### Listing Management

#### Adding a listing: `addListing`
<<<<<<< HEAD
Adds a property listing to the address book.

Format: `addListing pc/POSTAL_CODE (u/UNIT_NUMBER)(h/HOUSE_NUMBER)
 [lbp/LOWER_BOUND_PRICE] [ubp/UPPER_BOUND_PRICE] [n/PROPERTY_NAME] [t/TAG]... [nt/NEW_TAG]...`
=======
Adds a property listing to MatchEstate.

Format: `addListing pc/POSTAL_CODE (u/UNIT_NUMBER)(h/HOUSE_NUMBER)
[lbp/LOWER_BOUND_PRICE] [ubp/UPPER_BOUND_PRICE] [n/PROPERTY_NAME] [t/TAG]... [nt/NEW_TAG]...`

Input restriction:
* `POSTAL_CODE` must be exactly 6 digits, where each digit must be between 0 and 9.
* `UNIT_NUMBER` must be in the format of <optional B/R prefix><floor_number>-<apartment_number><optional_subunit> where the optional B/R represents basement or roof, the floor_number is 2 digits, the apartment number must be 2 to 5 digits, and subunit must be a capital letter.
* This subunit letter cannot be I or O.
* `HOUSE_NUMBER` must be at most 3 characters long, consisting of only letters and numbers. The last character cannot be 'I' or 'O'.
* A house number must not be specified with a unit number.
* `LOWER_BOUND_PRICE` and `UPPER_BOUND_PRICE` must be a non-negative number with up to 2 decimal places. If no value is given for a price, the price will be unbounded.
* `PROPERTY_NAME` must be between 2 and 100 characters long and can only contain letters, numbers, apostrophes, periods, hyphens, and spaces.
* `TAG` and `NEW_TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands.
* `TAG` tag cannot be blank and must already exist.
* `NEW_TAG` tag cannot be blank and must not already exist.
>>>>>>> master

Example:
* `addListing pc/654321 u/10-12`
* `addListing pc/654321 h/12 lbp/300000 ubp/600000 n/Sunny Villa t/quiet t/pet-friendly nt/family-friendly nt/spacious`
* `addListing pc/654321 u/10-12 lbp/30000 nt/spacious`

#### Listing all properties: `listListing`
Shows a list of all property listings.

Format: `listListing`

#### Searching properties by tags: `searchListingTag`
Finds properties with all specified tags.

<<<<<<< HEAD
Format: `searchListingTag t/TAG [t/TAG]...`

Example:
* `searchListingTag t/pet-friendly` 
* `searchListingTag t/pet-friendly t/pool`
=======
Format: `searchListingTag t/TAG...`

Input restriction:
* The search is case-insensitive.
* `TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands. 
* The tag cannot be blank and must already exist.

> ⚠️ **Note:** Each new search command will override the results of the previous search.  
For example, if you perform a `searchListingTag` followed by another `searchOwnerListing`, only the results from the second `searchOwnerListing` will be applied to all data. The filters do not stack.

Example:
* `searchListingTag t/pet-friendly t/pool` returns all listings **that contain ALL the specified tags (`pet-friendly` and `pool`)**.
>>>>>>> master

#### Searching owner properties: `searchOwnerListing`
Finds properties owned by a specific person.

Format: `searchOwnerListing PERSON_INDEX`

<<<<<<< HEAD
=======
Input restriction:
* The search is case-insensitive.
* `PERSON_INDEX` must be a positive integer within the bounds of person list.

> ⚠️ **Note:** Each new search command will override the results of the previous search.  
For example, if you perform a `searchListingTag` followed by another `searchOwnerListing`, only the results from the second `searchOwnerListing` will be applied to all data. The filters do not stack.

>>>>>>> master
Example:
* `searchOwnerListing 1`

#### Marking listing availability:
- `markAvailable`: Marks listing as available
- `markUnavailable`: Marks listing as unavailable

Format: `markAvailable LISTING_INDEX` or `markUnavailable LISTING_INDEX`

Listings marked as unavailable will not be matched to any person.

Example:
* `markAvailable 1`
* `markUnavailable 2`

#### Deleting a listing: `deleteListing`
<<<<<<< HEAD
Deletes the specified listing from the address book.
=======
Deletes the specified listing from MatchEstate.
>>>>>>> master

Format: `deleteListing LISTING_INDEX`

Example:
* `deleteListing 1`

### Tag Management

#### Adding tags: `addTag`
Adds new tags to the system.

<<<<<<< HEAD
Format: `addTag [nt/NEW_TAG]...`
=======
Format: `addTag nt/NEW_TAG...`

Input restriction:
* `NEW_TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands.
* `NEW_TAG` tag cannot be blank and must not already exist.
>>>>>>> master

Example:
* `addTag nt/family-friendly`
* `addTag nt/family-friendly nt/spacious`

<<<<<<< HEAD
#### Listing all tags: `listTag`
Shows a list of all available tags.

Format: `listTag`

#### Deleting tags: `deleteTag`
Deletes the specified tags from the system.

Format: `deleteTag [t/TAG]...`
=======
#### Deleting tags: `deleteTag`
Deletes the specified tags from the system.

Format: `deleteTag t/TAG...`

Input restriction:
* `TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands. 
* The tag cannot be blank and must already exist.
>>>>>>> master

Example:
* `deleteTag t/quiet`
* `deleteTag t/quiet t/pet-friendly`

### Preference Management

#### Adding a preference: `addPreference`
Adds a property preference to a person.

<<<<<<< HEAD
Format: `addPreference PERSON_INDEX lbp/LOWER_BOUND_PRICE ubp/UPPER_BOUND_PRICE [t/TAG]... [nt/NEW_TAG]...`
=======
Format: `addPreference PERSON_INDEX [lbp/LOWER_BOUND_PRICE] [ubp/UPPER_BOUND_PRICE] [t/TAG]... [nt/NEW_TAG]...`

Input restriction:
* `PERSON_INDEX ` must be a positive integer within the bounds of person list.
* `LOWER_BOUND_PRICE` and `UPPER_BOUND_PRICE` must be a non-negative number with up to 2 decimal places. If no value is given for a price, the price will be unbounded.
* `TAG` and `NEW_TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands.
* `TAG` tag cannot be blank and must already exist.
* `NEW_TAG` tag cannot be blank and must not already exist.
>>>>>>> master

Example:
* `addPreference 1`
* `addPreference 2 lbp/300000 ubp/600000 t/quiet t/pet-friendly nt/family-friendly nt/spacious`
* `addPreference 1 ubp/600000`

#### Adding tags to a preference: `addPreferenceTag`
Adds tags to an existing preference.

<<<<<<< HEAD
Format: `addPreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]... [nt/NEW_TAG]...`
=======
Format: `addPreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]{1}... [nt/NEW_TAG]{1}...`

Input restriction:
* `PERSON_INDEX` and `PREFERENCE_INDEX` must be a positive integer within the bounds of the person list and that person's preference list respectively.
* `TAG` and `NEW_TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands.
* `TAG` tag cannot be blank and must already exist.
* `NEW_TAG` tag cannot be blank and must not already exist.
>>>>>>> master

Example:
* `addPreferenceTag 2 1 t/quiet t/pet-friendly nt/family-friendly nt/spacious`
* `addPreferenceTag 2 1 t/quiet`
* `addPreferenceTag 2 1 nt/family-friendly`

#### Overwriting preference tags: `overwritePreferenceTag`
Replaces all tags in an existing preference.

<<<<<<< HEAD
Format: `overwritePreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]... [nt/NEW_TAG]...`
=======
Format: `overwritePreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]{1}... [nt/NEW_TAG]{1}...`

Input restriction:
* `PERSON_INDEX` and `PREFERENCE_INDEX` must be a positive integer within the bounds of the person list and that person's preference list respectively.
* `TAG` and `NEW_TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands.
* `TAG` tag cannot be blank and must already exist.
* `NEW_TAG` tag cannot be blank and must not already exist.
>>>>>>> master

Example:
* `overwritePreferenceTag 3 2 t/2-bedrooms`
* `overwritePreferenceTag 3 2 t/2-bedrooms nt/seaside-view`

#### Deleting a preference: `deletePreference`
Deletes a person's property preference.

Format: `deletePreference PERSON_INDEX PREFERENCE_INDEX`

Example:
* `deletePreference 1 2`

#### Deleting preference tags: `deletePreferenceTag`
Deletes tags from a person's preference.

<<<<<<< HEAD
Format: `deletePreferenceTag PERSON_INDEX PREFERENCE_INDEX [t/TAG]...`
=======
Format: `deletePreferenceTag PERSON_INDEX PREFERENCE_INDEX t/TAG...`

Input restriction:
* `PERSON_INDEX` and `PREFERENCE_INDEX` must be a positive integer within the bounds of the person list and that person's preference list respectively.
* `TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands. 
* The tag cannot be blank and must already exist.
>>>>>>> master

Example:
* `deletePreferenceTag 3 1 t/pet-friendly`
* `deletePreferenceTag 3 1 t/pet-friendly t/pool`

### Property Tag Management

#### Adding tags to listing: `addListingTag`
Adds tags to a property listing.

<<<<<<< HEAD
Format: `addListingTag LISTING_INDEX [t/TAG]... [nt/NEW_TAG]...`
=======
Format: `addListingTag LISTING_INDEX [t/TAG]{1}... [nt/NEW_TAG]{1}...`

Input restriction:
* `LISTING_INDEX` must be a positive integer within the bounds of the listings list.
* `TAG` and `NEW_TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands.
* `TAG` tag cannot be blank and must already exist.
* `NEW_TAG` tag cannot be blank and must not already exist.
>>>>>>> master

Example:
* `addListingTag 2 t/quiet`
* `addListingTag 2 t/quiet nt/family-friendly`
* `addListingTag 2 t/quiet t/pet-friendly nt/family-friendly nt/spacious`

#### Overwriting property tags: `overwriteListingTag`
Replaces all tags in a property listing.

<<<<<<< HEAD
Format: `overwriteListingTag LISTING_INDEX [t/TAG]... [nt/NEW_TAG]...`
=======
* After
<br>![addListingTagAfter](images/CS2103UG/addListingTagAfter.png)

#### Overwriting Listing Tags: `overwriteListingTag`
Replaces all tags in a listing.

Format: `overwriteListingTag LISTING_INDEX [t/TAG]{1}... [nt/NEW_TAG]{1}...`

Input restriction:
* `LISTING_INDEX` must be a positive integer within the bounds of the listings list.
* `TAG` and `NEW_TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands.
* `TAG` tag cannot be blank and must already exist.
* `NEW_TAG` tag cannot be blank and must not already exist.
>>>>>>> master

Example:
* `overwriteListingTag 3 t/4-bedrooms`
* `overwriteListingTag 3 nt/seaside-view`
* `overwriteListingTag 3 t/4-bedrooms t/2-toilets nt/seaside-view`

#### Deleting property tags: `deleteListingTag`
Deletes tags from a property.

<<<<<<< HEAD
Format: `deleteListingTag PROPERTY_INDEX [t/TAG]...`
=======
* After
<br>![overwriteListingTagAfter](images/CS2103UG/overwriteListingTagAfter.png)

#### Delete Listing Tags: `deleteListingTag`
Deletes tags from a listing.

Format: `deleteListingTag LISTING_INDEX t/TAG...`

Input restriction:
* `LISTING_INDEX ` must be a positive integer within the bounds of the listings list.
* `TAG` must be between 2 and 30 characters long and can only contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus, and ampersands. 
* The tag cannot be blank and must already exist.
>>>>>>> master

Example:
* `deleteListingTag 3 t/pet-friendly`
* `deleteListingTag 3 t/pet-friendly t/pool`

### Matching System

#### Matching person's preference to listings: `matchPreference`
Finds listings matching a person's preference.

Format: `matchPreference PERSON_INDEX PREFERENCE_INDEX`

Example:
* `matchPreference 1 2`

Listings that are marked as unavailable or owned by the person will not be shown.

#### Matching listings to persons: `matchListing`
Finds persons whose preferences match a listing.

Note: matchListing works for unavailable listings such that the users can still look for buyers while the listing is on hold

Format: `matchListing LISTING_INDEX`

Example:
* `matchListing 1`

### Owner Management

#### Assigning an owner to a listing: `addOwner`
Assigns a person as owner to a listing.

Format: `addOwner PERSON_INDEX LISTING_INDEX`

Example:
* `addOwner 2 1`

#### Deleting an owner: `deleteOwner`
Removes an owner from a listing.

Format: `deleteOwner LISTING_INDEX OWNER_INDEX`

Example:
* `deleteOwner 1 2`


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
<<<<<<< HEAD
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.
=======
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous MatchEstate home folder.
>>>>>>> master

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------
