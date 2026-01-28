ALTER TABLE "participant" ADD FOREIGN KEY ("participant_type_id") REFERENCES "participant_type" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "participant" ADD FOREIGN KEY ("professional_school_id") REFERENCES "professional_school" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "participant_congress" ADD FOREIGN KEY ("congress_id") REFERENCES "congress" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "participant_congress" ADD FOREIGN KEY ("participant_id") REFERENCES "participant" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "user" ADD FOREIGN KEY ("participant_type_id") REFERENCES "participant_type" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "refreshment" ADD FOREIGN KEY ("participant_congress_id") REFERENCES "participant_congress" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "congress" ADD FOREIGN KEY ("professional_school_id") REFERENCES "professional_school" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "user" ADD FOREIGN KEY ("professional_school_id") REFERENCES "professional_school" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "presentation" ADD FOREIGN KEY ("speaker_id") REFERENCES "speaker" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "presentation" ADD FOREIGN KEY ("congress_id") REFERENCES "congress" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "speaker" ADD FOREIGN KEY ("congress_id") REFERENCES "congress" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "speaker" ADD FOREIGN KEY ("academic_degree_id") REFERENCES "academic_degree" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "comment" ADD FOREIGN KEY ("participant_id") REFERENCES "participant" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "presentation_registration" ADD FOREIGN KEY ("participant_congress_id") REFERENCES "participant_congress" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "presentation_registration" ADD FOREIGN KEY ("presentation_id") REFERENCES "presentation" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "presentation_registration" ADD FOREIGN KEY ("time_block_id") REFERENCES "time_block" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "vote" ADD FOREIGN KEY ("participant_congress_id") REFERENCES "participant_congress" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "vote" ADD FOREIGN KEY ("presentation_id") REFERENCES "presentation" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "conference_day" ADD FOREIGN KEY ("congress_id") REFERENCES "congress" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "time_block" ADD FOREIGN KEY ("conference_day_id") REFERENCES "conference_day" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "time_block" ADD FOREIGN KEY ("location_id") REFERENCES "location" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "attendance" ADD FOREIGN KEY ("participant_congress_id") REFERENCES "participant_congress" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "attendance" ADD FOREIGN KEY ("time_block_id") REFERENCES "time_block" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "congress_user" ADD FOREIGN KEY ("congress_id") REFERENCES "congress" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "congress_user" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id") ON DELETE CASCADE ON UPDATE CASCADE;