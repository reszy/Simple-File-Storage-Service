package pl.reszy.filestorage.domain

import spock.lang.Specification
import spock.lang.Unroll

class WEBIDSpec extends Specification {

    def "should convert from UUID to WEBID"() {
        given:
        def uuid = UUID.fromString('6d907b60-76cd-11ea-93c7-2718bc515404')

        when:
        def webId = WEBID.fromUUID(uuid)

        then:
        webId.toString() == 'Rp9KcyFQsARoCeq2RmEYm9Rt6OatFcs4KZkz'
    }

    def "should convert from WEBID to UUID"() {
        given:
        def webId = WEBID.fromString('Rp9KcyFQsARoCeq2RmEYm9Rt6OatFcs4KZkz')

        when:
        def uuid = webId.toUUID()

        then:
        uuid.toString() == '6d907b60-76cd-11ea-93c7-2718bc515404'
    }

    @Unroll
    def "should convert to hex #hexValue"() {
        when:
        char result = WEBID.toHexCharacter(hexValue)

        then:
        result == hexChar.charAt(0)

        where:
        hexValue||hexChar
        0||'0'
        1||'1'
        2||'2'
        3||'3'
        4||'4'
        5||'5'
        6||'6'
        7||'7'
        8||'8'
        9||'9'
        10||'a'
        11||'b'
        12||'c'
        13||'d'
        14||'e'
        15||'f'
        17||'-'
    }
}