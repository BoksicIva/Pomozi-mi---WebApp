import Geocode from "react-geocode";

Geocode.setApiKey(process.env.REACT_APP_GOOGLE_MAPS_API_KEY);

class LocationService {
    async getLatLong(country, town, address) {
        const location = {};
        await Geocode.fromAddress(country + " " + town + " " + address).then(
            response => {
                const { lat, lng } = response.results[0].geometry.location;
                console.log(lat, lng);
                location.longitude = lng;
                location.latitude = lat;
            },
            error => {
                console.error(error);
            }
        );

        location.state = country;
        location.town = town;
        location.adress = address;

        return location;
    }
    
}

export default new LocationService();